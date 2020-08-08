package com.ek.kotlinmvp.environment

import com.ek.kotlinmvp.data.local.rickAndMorty.RickAndMorty
import com.ek.kotlinmvp.data.common.RAM_BASE_URL
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface IRickAndMortyAPI {
    @GET("https://rickandmortyapi.com/api/character/")
    fun getCharacterPage(@Query("page") page:Int): Call<RickAndMorty>

    companion object Factory {
        fun create(): IRickAndMortyAPI {
            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(RAM_BASE_URL)
                .build()

            return retrofit.create(IRickAndMortyAPI::class.java)
        }
    }
}