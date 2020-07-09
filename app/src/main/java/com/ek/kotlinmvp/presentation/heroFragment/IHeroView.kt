package com.ek.kotlinmvp.presentation.heroFragment

import RickAndMorty

interface IHeroView {
    fun onDataCompleteFromAPI(rickAndMorty: RickAndMorty)
    fun onDataErrorFromAPI(throwable: Throwable)
}