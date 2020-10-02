package com.ek.kotlinmvp.presentation.homeFragment

import com.ek.kotlinmvp.data.db.entity.Hero

interface AdapterCallback {
    fun onItemClicked(item: Hero)
}