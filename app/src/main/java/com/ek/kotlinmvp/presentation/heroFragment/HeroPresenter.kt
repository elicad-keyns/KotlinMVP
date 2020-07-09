package com.ek.kotlinmvp.presentation.heroFragment

import RickAndMorty
import com.ek.kotlinmvp.environment.IRickAndMortyAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HeroPresenter(internal var IHeroView: IHeroView) :
    IHeroPresenter {

    override fun getDataFromAPI(page: Int) {
        IRickAndMortyAPI.create()
            .getCharacterPage(page = page)
            .enqueue(object : Callback<RickAndMorty> {
                override fun onFailure(call: Call<RickAndMorty>, t: Throwable) {
                    IHeroView.onDataErrorFromAPI(t)
                }

                override fun onResponse(call: Call<RickAndMorty>, response: Response<RickAndMorty>) {
                    IHeroView.onDataCompleteFromAPI(response.body() as RickAndMorty)
                }
            })
    }
}