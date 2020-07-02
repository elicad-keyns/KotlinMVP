package com.ek.kotlinmvp.presentation.RickAndMorty

import RickAndMorty
import com.ek.kotlinmvp.environment.IRickAndMortyAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RAMPresenter(internal var IRAMView: IRAMView) : IRAMPresenter {

    override fun getDataFromAPI(page: Int) {
        IRickAndMortyAPI.create()
            .getCharacterPage(page = page)
            .enqueue(object : Callback<RickAndMorty> {
                override fun onFailure(call: Call<RickAndMorty>, t: Throwable) {
                    IRAMView.onDataErrorFromAPI(t)
                }

                override fun onResponse(call: Call<RickAndMorty>, response: Response<RickAndMorty>) {
                    IRAMView.onDataCompleteFromAPI(response.body() as RickAndMorty)
                }
            })
    }
}