package com.ek.kotlinmvp.presentation.homeFragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ek.kotlinmvp.R.color
import com.ek.kotlinmvp.R.layout
import com.ek.kotlinmvp.common.Status
import com.ek.kotlinmvp.common.StatusColor
import com.ek.kotlinmvp.data.db.entity.Hero
import com.ek.kotlinmvp.other.MainApplication
import kotlinx.android.synthetic.main.list_item.view.*

class HeroDBAdapter(var items: ArrayList<Hero>, private val adapterCallback: AdapterCallback) :
    RecyclerView.Adapter<HeroDBAdapter.HeroHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        HeroHolder(LayoutInflater.from(parent.context).inflate(layout.list_item, parent, false))

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: HeroHolder, position: Int) {
        val item = items[position]
        holder.itemView.apply {
            when (item.hero_status) {
                Status.Alive.name -> heroStatus.setTextColor( StatusColor.AliveColor.color )
                Status.Dead.name -> heroStatus.setTextColor( StatusColor.DeadColor.color )
                Status.unknown.name -> heroStatus.setTextColor( StatusColor.UnknownColor.color )
            }

            heroId.text = item.hero_id
            heroStatus.text = item.hero_status
            heroName.text = item.hero_name
            heroGender.text = item.hero_gender
            Glide.with(MainApplication.context).load(item.hero_image).into(heroImage)

            setOnClickListener {
                if (holder.adapterPosition != RecyclerView.NO_POSITION) adapterCallback.onItemClicked(items[holder.adapterPosition])
            }
        }
    }

    inner class HeroHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    fun insert(newHeroes: ArrayList<Hero>) {
        items.addAll(newHeroes)
        this.notifyDataSetChanged()
    }

    fun clearData() {
        items.clear()
        this.notifyDataSetChanged()
    }
}