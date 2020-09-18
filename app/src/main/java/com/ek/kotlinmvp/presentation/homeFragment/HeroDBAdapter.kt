package com.ek.kotlinmvp.presentation.homeFragment

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ek.kotlinmvp.R.color
import com.ek.kotlinmvp.R.layout
import com.ek.kotlinmvp.data.db.entity.Hero
import com.ek.kotlinmvp.other.MainApplication
import kotlinx.android.synthetic.main.list_item.view.*


class HeroDBAdapter(var items: ArrayList<Hero>, private val callback: Callback) :
    RecyclerView.Adapter<HeroDBAdapter.HeroHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        HeroHolder(LayoutInflater.from(parent.context).inflate(layout.list_item, parent, false))

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: HeroHolder, position: Int) {
        val item = items[position]
        holder.itemView.apply {
            when (item.hero_status) {
                "Alive" -> holder.itemView.heroStatus.setTextColor(
                    ContextCompat.getColor(
                        MainApplication.context,
                        color.colorAccent
                    )
                )
                "Dead" -> holder.itemView.heroStatus.setTextColor(
                    ContextCompat.getColor(
                        MainApplication.context,
                        color.colorVinous
                    )
                )
                "unknown" -> holder.itemView.heroStatus.setTextColor(Color.GRAY)
            }

            holder.itemView.heroId.text = item.hero_id.toString()
            holder.itemView.heroStatus.text = item.hero_status
            holder.itemView.heroName.text = item.hero_name
            holder.itemView.heroGender.text = item.hero_gender
            Glide.with(MainApplication.context).load(item.hero_image).into(holder.itemView.heroImage)

            holder.itemView.setOnClickListener {
                if (holder.adapterPosition != RecyclerView.NO_POSITION) callback.onItemClicked(items[holder.adapterPosition])
            }
        }
    }

    inner class HeroHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

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
}