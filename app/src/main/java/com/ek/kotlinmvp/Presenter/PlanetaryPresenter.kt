package com.ek.kotlinmvp.Presenter

import android.content.Context
import com.ek.kotlinmvp.Api.ApiNasaInterface
import com.ek.kotlinmvp.Model.Planetary
import com.ek.kotlinmvp.View.IPlanetaryView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PlanetaryPresenter(internal var IPlanetaryView:IPlanetaryView):IPlanetaryPresenter {

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