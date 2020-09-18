package com.ek.kotlinmvp.environment

import com.ek.kotlinmvp.data.net.RickAndMorty
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface IRickAndMortyAPI {
    @GET("https://rickandmortyapi.com/api/character/")
    fun getCharacterPage(@Query("page") page:Int): Call<RickAndMorty>
}