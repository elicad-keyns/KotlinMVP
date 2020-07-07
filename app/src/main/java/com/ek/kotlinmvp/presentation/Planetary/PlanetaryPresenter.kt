package com.ek.kotlinmvp.presentation.planetary

import com.ek.kotlinmvp.environment.ApiNasaInterface
import com.ek.kotlinmvp.data.local.nasa.Planetary
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PlanetaryPresenter(internal var IPlanetaryView: IPlanetaryView): IPlanetaryPresenter {

    override fun getDataFromApi() {
        ApiNasaInterface.create()
            .getPlanetary()
            .enqueue(object : Callback<Planetary> {
                override fun onFailure(call: Call<Planetary>, t: Throwable) {
                    IPlanetaryView.onDataErrorFromApi(t)
                }

                override fun onResponse(call: Call<Planetary>, response: Response<Planetary>) {
                    IPlanetaryView.onDataCompleteFromApi(response.body() as Planetary)
                }

            })
    }
}