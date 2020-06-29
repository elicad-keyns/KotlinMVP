package com.ek.kotlinmvp.View

import com.ek.kotlinmvp.Model.Planetary

interface IPlanetaryView {
    fun onDataCompleteFromApi(planetary: Planetary)
    fun onDataErrorFromApi(throwable: Throwable)
}