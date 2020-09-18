package com.ek.kotlinmvp.presentation.heroFragment

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ek.kotlinmvp.R
import com.ek.kotlinmvp.data.db.entity.Hero

class HeroDBAdapter(var items: List<Hero>, val context: Context, val callback: Callback) :
    RecyclerView.Adapter<HeroDBAdapter.HeroHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        HeroHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false))

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: HeroHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class HeroHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val heroId = itemView.findViewById<TextView>(R.id.heroId)
        private val heroStatus = itemView.findViewById<TextView>(R.id.heroStatus)
        private val heroName = itemView.findViewById<TextView>(R.id.heroName)
        private val heroGender = itemView.findViewById<TextView>(R.id.heroGender)
        private val heroImage = itemView.findViewById<ImageView>(R.id.heroImage)

        fun bind(item: Hero) {

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

}