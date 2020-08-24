package com.ek.kotlinmvp.presentation.homeFragment

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.ColorInt
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ek.kotlinmvp.R
import com.ek.kotlinmvp.data.db.entity.Hero
import java.util.*
import kotlin.collections.ArrayList


class HeroDBAdapter(var items: ArrayList<Hero>, val context: Context, recyclerView: RecyclerView, val callback: Callback) :
    RecyclerView.Adapter<HeroDBAdapter.HeroHolder>() {

//    internal var colors: List<String> = listOf(
//        "#eab595",
//        "#d87f81",
//        "#ae6378",
//        "#79616f",
//        "#7e9680"
//    )

    internal var loadMore: ILoadHero? = null
    internal var isLoading: Boolean = false
    internal var visibleThreshold: Int = 1
    internal var lastVisibleItem: Int = 0
    internal var totalItemCount: Int = 0

    init {
        val linearLayoutManager: LinearLayoutManager =
            recyclerView.layoutManager as LinearLayoutManager
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                totalItemCount = linearLayoutManager.itemCount
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition()
                Toast.makeText(context, "Всего: $totalItemCount\nЛаст: $lastVisibleItem", Toast.LENGTH_SHORT).show()
                if (!isLoading && visibleThreshold >= totalItemCount - lastVisibleItem)
                    if (loadMore != null) {
                        loadMore!!.onLoadHero()
                        //Test
                        loadMore!!.onOpenLoading()
                        isLoading = true
                    }
            }
        })
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        HeroHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false))

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: HeroHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class HeroHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val heroId = itemView.findViewById<TextView>(R.id.hero_id)
        private val heroStatus = itemView.findViewById<TextView>(R.id.hero_status)
        private val heroName = itemView.findViewById<TextView>(R.id.hero_name)
        private val heroGender = itemView.findViewById<TextView>(R.id.hero_gender)
        private val heroImage = itemView.findViewById<ImageView>(R.id.hero_image)
        private val heroBackground = itemView.findViewById<CardView>(R.id.cv_hero)

        fun bind(item: Hero) {

            // Test
            // heroBackground.setCardBackgroundColor(Color.parseColor(colors.random()))

            if (item.hero_status == "Alive")
                heroStatus.setTextColor(Color.GREEN)
            else if (item.hero_status == "Dead")
                heroStatus.setTextColor(Color.RED)

            heroId.text = item.hero_id.toString()
            heroStatus.text = item.hero_status
            heroName.text = item.hero_name
            heroGender.text = item.hero_gender
            Glide.with(context).load(item.hero_image).into(heroImage)

            itemView.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) callback.onItemClicked(items[adapterPosition])
            }
        }
    }

    interface Callback {
        fun onItemClicked(item: Hero)
    }

    fun insert(newHeroes:ArrayList<Hero>){
        items.addAll(newHeroes)
        this.notifyDataSetChanged()
    }

    fun clearData() {
        items.clear()
        this.notifyDataSetChanged()
    }

    fun isLoaded() {
        isLoading = false
    }

    fun loaderIsEmpty(): Boolean {
        return this.loadMore == null
    }

    fun setLoader(iLoadMore: ILoadHero) {
        this.loadMore = iLoadMore
    }

}