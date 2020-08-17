package com.ek.kotlinmvp.presentation.homeFragment

import android.content.Context
import android.widget.Toast
import com.ek.kotlinmvp.data.db.HeroDatabase
import com.ek.kotlinmvp.data.db.dao.HeroDao
import com.ek.kotlinmvp.data.db.entity.Hero
import com.ek.kotlinmvp.data.local.rickAndMorty.RickAndMorty
import com.ek.kotlinmvp.environment.IRickAndMortyAPI
import moxy.InjectViewState
import moxy.MvpPresenter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@InjectViewState
class HomePresenter() : MvpPresenter<IHomeView>() {

    lateinit var context: Context

    // текущая страница
    var page: Int = 1

    // Максимальное кол-во страниц
    var maxPages: Int? = null

    // Первый запуск вьюшки
    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        getDataFromAPI(page = 1)
        //checkConnection(context)
    }

    fun recordData(rickAndMorty: RickAndMorty) {
        val db: HeroDatabase? = HeroDatabase.getHeroDatabase(context = context)
        val heroDao: HeroDao? = db?.heroDao()

        maxPages = rickAndMorty.info.pages

        for (result in rickAndMorty.results) {
            val hero = Hero(
                hero_id = result.id,
                hero_name = result.name,
                hero_status = result.status,
                hero_species = result.species,
                hero_type = result.type,
                hero_gender = result.gender,
                hero_origin_name = result.origin.name,
                hero_location_name = result.location.name,
                hero_image = result.image,
                hero_created = result.created,
                hero_page = page,
                hero_max_pages = maxPages
            )
            heroDao?.insertHero(hero)
        }

        Toast.makeText(context, "Record Data:\nPage ->$page\nMaxPages -> $maxPages", Toast.LENGTH_SHORT).show()
    }

    // Старт запроса и получение инфы
    private fun getDataFromAPI(page: Int) {
        IRickAndMortyAPI.create()
            .getCharacterPage(page = page)
            .enqueue(object : Callback<RickAndMorty> {
                override fun onFailure(call: Call<RickAndMorty>, t: Throwable) {
                    // viewState.onDataErrorFromAPI(t)
                    viewState.getDataFromDB(page = page)
                }

                override fun onResponse(
                    call: Call<RickAndMorty>,
                    response: Response<RickAndMorty>
                ) {
                    //viewState.onDataCompleteFromAPI(response.body() as RickAndMorty)
                    recordData(response.body() as RickAndMorty)
                    viewState.getDataFromDB(page = page)

                }
            })
    }

}