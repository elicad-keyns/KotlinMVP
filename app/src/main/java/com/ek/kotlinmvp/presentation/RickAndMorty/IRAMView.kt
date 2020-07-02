package com.ek.kotlinmvp.presentation.RickAndMorty

import RickAndMorty
import com.ek.kotlinmvp.data.local.Planetary

interface IRAMView {
    fun onDataCompleteFromAPI(rickAndMorty: RickAndMorty)
    fun onDataErrorFromAPI(throwable: Throwable)
}