package com.ek.kotlinmvp.presentation.planetary

import com.ek.kotlinmvp.data.local.nasa.Planetary

interface IPlanetaryView {
    fun onDataCompleteFromApi(planetary: Planetary)
    fun onDataErrorFromApi(throwable: Throwable)
}