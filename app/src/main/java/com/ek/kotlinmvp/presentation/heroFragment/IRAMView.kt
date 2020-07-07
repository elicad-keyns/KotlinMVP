package com.ek.kotlinmvp.presentation.heroFragment

import RickAndMorty

interface IRAMView {
    fun onDataCompleteFromAPI(rickAndMorty: RickAndMorty)
    fun onDataErrorFromAPI(throwable: Throwable)
}