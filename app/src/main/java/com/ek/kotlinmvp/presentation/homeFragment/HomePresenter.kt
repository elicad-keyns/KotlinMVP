package com.ek.kotlinmvp.presentation.homeFragment

import android.widget.Toast
import com.ek.kotlinmvp.data.db.HeroDatabase
import com.ek.kotlinmvp.data.db.dao.HeroDao
import com.ek.kotlinmvp.data.db.entity.Hero
import com.ek.kotlinmvp.data.local.rickAndMorty.LocalRAM
import com.ek.kotlinmvp.data.mapper.toLocalRAM
import com.ek.kotlinmvp.data.net.RickAndMorty
import com.ek.kotlinmvp.environment.Factory
import com.ek.kotlinmvp.other.MainApplication
import moxy.InjectViewState
import moxy.MvpPresenter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@InjectViewState
class HomePresenter : MvpPresenter<IHomeView>(){

    // текущая страница
    private var heroPage: Int = 1

    // Максимальное кол-во страниц
    private var maxPages: Int? = null

    // Первый запуск вьюшки
    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        viewState.createAdapter()
        viewState.setScrollListener()
        getDataFromAPI(heroPage)

        viewState.setRefreshing()
    }

    fun recordData(rickAndMorty: RickAndMorty) {
        val db: HeroDatabase? = HeroDatabase.getHeroDatabase()
        val heroDao: HeroDao? = db?.heroDao()

        val localRAM: LocalRAM = rickAndMorty.toLocalRAM()
        maxPages = localRAM.localInfo.localPages

        for (result in localRAM.localResults) {
            val hero = Hero(
                hero_id = result.localId,
                hero_name = result.localName,
                hero_status = result.localStatus,
                hero_species = result.localSpecies,
                hero_type = result.localType,
                hero_gender = result.localGender,
                hero_origin_name = result.localOrigin.localName,
                hero_location_name = result.localLocation.localName,
                hero_image = result.localImage,
                hero_created = result.localCreated,
                hero_page = heroPage,
                hero_max_pages = maxPages
            )
            heroDao?.insertHero(hero)
        }
        Toast.makeText(MainApplication.context, "Record Data:\nPage ->$heroPage\nMaxPages -> $maxPages", Toast.LENGTH_SHORT).show()
    }

    // Запрос и получение инфы с апи
    private fun getDataFromAPI(_page: Int) {
        viewState.isRefreshing()

        Factory.create()
            .getCharacterPage(_page)
            .enqueue(object : Callback<RickAndMorty> {
                override fun onFailure(call: Call<RickAndMorty>, t: Throwable) {
                    getDataFromDB(_page)
                }

                override fun onResponse(
                    call: Call<RickAndMorty>,
                    response: Response<RickAndMorty>
                ) {
                    recordData(response.body() as RickAndMorty)
                    getDataFromDB(_page)
                }
            })
    }

    // Запрос к бд
    fun getDataFromDB(_page: Int) {
        val db: HeroDatabase? = HeroDatabase.getHeroDatabase()
        val heroDao: HeroDao? = db?.heroDao()

        val heroes: ArrayList<Hero> = ArrayList(heroDao!!.getHeroesByPage(hero_page = _page))

        // Получаем макс страницы с бд
        if (maxPages == null)
            if (heroes[0].hero_max_pages != null)
                maxPages = heroes[0].hero_max_pages

        viewState.addHeroes(_heroes = heroes)
        viewState.isLoaded()
        viewState.isRefreshed()
    }

    fun resetData(adapter: HeroDBAdapter) {
        adapter.clearData()
        heroPage = 1
        getDataFromAPI(heroPage)
    }

    fun getNewHeroes() {
        ++heroPage
        getDataFromAPI(heroPage)
    }
}