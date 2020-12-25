package com.ek.kotlinmvp.presentation.homeFragment

import android.app.Person
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ek.kotlinmvp.R
import com.ek.kotlinmvp.common.Status
import com.ek.kotlinmvp.common.StatusColor
import com.ek.kotlinmvp.data.db.entity.Hero
import com.ek.kotlinmvp.other.MainApplication
import com.ek.kotlinmvp.other.MainApplication.Companion.context
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.android.synthetic.main.list_item.view.*
import kotlinx.android.synthetic.main.loading_item.view.*

internal class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

internal class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

class HeroAdapter(
    var items: ArrayList<Hero?> = arrayListOf(),
    private val adapterCallback: AdapterCallback
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val VIEW_ITEMTYPE = 0
    private val VIEW_LOADINGTYPE = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_ITEMTYPE) {
            ItemViewHolder(
                LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)
            )
        } else {
            LoadingViewHolder(
                LayoutInflater.from(context).inflate(R.layout.loading_item, parent, false)
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ItemViewHolder) {
            val item = items[position]!!
            holder.itemView.apply {
                cvHero.animation =
                    AnimationUtils.loadAnimation(context, R.anim.fade_in_animation)

                when (item.hero_status) {
                    Status.Alive.name -> heroStatus.setTextColor(StatusColor.AliveColor.color)
                    Status.Dead.name -> heroStatus.setTextColor(StatusColor.DeadColor.color)
                    Status.unknown.name -> heroStatus.setTextColor(StatusColor.UnknownColor.color)
                }

                heroId.text = item.hero_id
                heroStatus.text = item.hero_status
                heroName.text = item.hero_name
                heroGender.text = item.hero_gender
                Glide.with(MainApplication.context).load(item.hero_image).into(heroImage)

                cvHeroImage.animation =
                    AnimationUtils.loadAnimation(context, R.anim.fade_in_animation)

                setOnClickListener {
                    if (holder.adapterPosition != RecyclerView.NO_POSITION) adapterCallback.onItemClicked(
                        items[holder.adapterPosition]!!
                    )
                }
            }
        } else if (holder is LoadingViewHolder) {
            holder.itemView.cvLoading.animation =
                AnimationUtils.loadAnimation(context, R.anim.fade_in_animation)
            holder.itemView.progressBar.isIndeterminate = true
        }
    }

    private val y = 1;
    
    override fun getItemViewType(position: Int): Int {
        return if (items[position] == null) VIEW_LOADINGTYPE else VIEW_ITEMTYPE
    }

    override fun getItemCount(): Int = items.size

    fun insert(newHeroes: ArrayList<Hero?>) {
        val diffUtilsCallback = DiffUtilsCallback(items, (items  + newHeroes) as ArrayList<Hero?>)
        val diffResult= DiffUtil.calculateDiff(diffUtilsCallback)
        items.remove(null)
        items.addAll(newHeroes)
        diffResult.dispatchUpdatesTo(this)
    }

    fun insertLoader() {
        items.add(null)
        this.notifyItemInserted(items.size - 1)
    }

    fun clearData() {
        items.clear()
        this.notifyDataSetChanged()
    }
}