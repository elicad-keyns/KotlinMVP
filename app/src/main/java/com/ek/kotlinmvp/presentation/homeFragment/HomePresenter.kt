package com.ek.kotlinmvp.presentation.homeFragment

import android.util.Range
import com.ek.kotlinmvp.common.LoadStatus
import com.ek.kotlinmvp.data.db.HeroDatabase
import com.ek.kotlinmvp.data.db.dao.HeroDao
import com.ek.kotlinmvp.data.db.entity.Hero
import com.ek.kotlinmvp.data.local.rickAndMorty.LocalRAM
import com.ek.kotlinmvp.data.mapper.toLocalRAM
import com.ek.kotlinmvp.data.net.RickAndMorty
import com.ek.kotlinmvp.environment.Factory
import moxy.InjectViewState
import moxy.MvpPresenter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@InjectViewState
class HomePresenter : MvpPresenter<IHomeView>(), LoadMore{

    private var currentPage: Int = 1
    private var maxPages: Int? = null

    private var heroes: ArrayList<Hero?> = ArrayList()

    var isLoading = false
    var loadMore: LoadMore? = null

    // Первый запуск вьюшки
    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        loadMore = this
        viewState.setRefreshing()
        getDataFromAPI(currentPage)
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
                hero_page = currentPage,
                hero_max_pages = maxPages
            )
            heroDao?.insertHero(hero)
        }
    }

    private fun getDataFromAPI(_page: Int) {
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

    fun getDataFromDB(_page: Int) {
        val db: HeroDatabase? = HeroDatabase.getHeroDatabase()
        val heroDao: HeroDao? = db?.heroDao()
        heroes = ArrayList(heroDao!!.getHeroesByPage(hero_page = _page))

        if (maxPages == null)
            if (heroes.isNotEmpty())
                if (heroes[0]!!.hero_max_pages != null)
                    maxPages = heroes[0]!!.hero_max_pages

        viewState.addHeroes(_heroes = heroes)
        isLoading = false
        viewState.isRefresh(LoadStatus.Off)
    }

    fun resetData(adapter: HeroAdapter) {
        isLoading = true
        adapter.clearData()
        currentPage = 1
        getDataFromAPI(currentPage)
    }

    private fun getNewHeroes() {
        ++currentPage
        getDataFromAPI(currentPage)
    }

    override fun onLoadMore() {
        isLoading = true
        getNewHeroes()
    }
}