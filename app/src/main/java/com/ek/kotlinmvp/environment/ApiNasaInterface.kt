package com.ek.kotlinmvp.environment

import com.ek.kotlinmvp.data.local.nasa.Planetary
import retrofit2.Call
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ApiNasaInterface {
    @GET("planetary/apod?api_key=rtoz2O55Mg9nr15ibeUmZLCkwLTXzImFkVlf4MTn")
    fun getPlanetary(): Call<Planetary>

    companion object Factory {
        fun create(): ApiNasaInterface {
            val retrofit = retrofit2.Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://api.nasa.gov/")
                .build()

            return retrofit.create(ApiNasaInterface::class.java)
        }
    }
}