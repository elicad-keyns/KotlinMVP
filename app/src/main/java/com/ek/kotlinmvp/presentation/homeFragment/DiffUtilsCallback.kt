package com.ek.kotlinmvp.presentation.homeFragment

import androidx.recyclerview.widget.DiffUtil
import com.ek.kotlinmvp.data.db.entity.Hero

class DiffUtilsCallback(
    private val oldHeroes: ArrayList<Hero?>,
    private val newHeroes: ArrayList<Hero?>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldHeroes.size

    override fun getNewListSize(): Int = newHeroes.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldHeroes[oldItemPosition]?.hero_id == newHeroes[newItemPosition]?.hero_id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldHeroes[oldItemPosition] == newHeroes[newItemPosition]
    }
}