package com.ek.kotlinmvp.environment

import com.ek.kotlinmvp.data.common.RAM_BASE_URL
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object Factory {
    fun create(): IRickAndMortyAPI {
        val retrofit = Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(RAM_BASE_URL)
            .build()

        return retrofit.create(IRickAndMortyAPI::class.java)
    }
}