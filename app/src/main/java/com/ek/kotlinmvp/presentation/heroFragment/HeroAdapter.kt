package com.ek.kotlinmvp.presentation.heroFragment

import Results
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ek.kotlinmvp.R

class HeroAdapter(var items: List<Results>, var fragment: Fragment, val callback: Callback) :
    RecyclerView.Adapter<HeroAdapter.HeroHolder>() {

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

        fun bind(item: Results) {

            if (item.status == "Alive")
                heroStatus.setTextColor(Color.GREEN)
            else if (item.status == "Dead")
                heroStatus.setTextColor(Color.RED)

            heroId.text = item.id.toString()
            heroStatus.text = item.status
            heroName.text = item.name
            heroGender.text = item.gender
            Glide.with(fragment).load(item.image).into(heroImage)

            itemView.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) callback.onItemClicked(items[adapterPosition])
            }
        }
    }

    interface Callback {
        fun onItemClicked(item: Results)
    }

}