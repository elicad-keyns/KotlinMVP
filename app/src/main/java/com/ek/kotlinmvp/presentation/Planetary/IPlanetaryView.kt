package com.ek.kotlinmvp.presentation.Planetary

import com.ek.kotlinmvp.data.local.Planetary

interface IPlanetaryView {
    fun onDataCompleteFromApi(planetary: Planetary)
    fun onDataErrorFromApi(throwable: Throwable)
}