package com.ek.kotlinmvp.presentation.homeFragment

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
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
class HomePresenter : MvpPresenter<IHomeView>(), ILoadHero {

    lateinit var context: Context
    lateinit var view: View

    private lateinit var rv_home_heroes: RecyclerView

    // текущая страница
    var heroPage: Int = 1

    // Максимальное кол-во страниц
    var maxPages: Int? = null

    lateinit var heroDBAdapter: HeroDBAdapter

    var heroes: ArrayList<Hero> = ArrayList()

    // Первый запуск вьюшки
    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        createAdapter()

        getDataFromAPI(page = heroPage)
        viewState.setRefreshing()
        viewState.openLoading()
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
                hero_page = heroPage,
                hero_max_pages = maxPages
            )
            heroDao?.insertHero(hero)
        }
        Toast.makeText(context, "Record Data:\nPage ->$heroPage\nMaxPages -> $maxPages", Toast.LENGTH_SHORT).show()
    }

    // Старт запроса и получение инфы
    private fun getDataFromAPI(page: Int) {
        IRickAndMortyAPI.create()
            .getCharacterPage(page = page)
            .enqueue(object : Callback<RickAndMorty> {
                override fun onFailure(call: Call<RickAndMorty>, t: Throwable) {
                    viewState.openLoading()
                    getDataFromDB(page = page)
                }

                override fun onResponse(
                    call: Call<RickAndMorty>,
                    response: Response<RickAndMorty>
                ) {
                    recordData(response.body() as RickAndMorty)
                    getDataFromDB(page = page)
                }
            })
    }

    fun getDataFromDB(page: Int) {
        val db: HeroDatabase? = HeroDatabase.getHeroDatabase(context = context)
        val heroDao: HeroDao? = db?.heroDao()

        heroes = ArrayList(heroDao!!.getHeroesByPage(hero_page = page))

        // Получаем макс страницы с бд
        if (maxPages == null)
            if (heroes[0].hero_max_pages != null)
                maxPages = heroes[0].hero_max_pages

        heroDBAdapter.insert(heroes)

        heroDBAdapter.isLoaded()
        viewState.hideLoading()
        viewState.isRefreshed()
    }

    private fun createAdapter() {
        heroDBAdapter = HeroDBAdapter(
            heroes,
            context,
            rv_home_heroes,
            object : HeroDBAdapter.Callback {
                override fun onItemClicked(item: Hero) {
                    val action = HomeFragmentDirections.actionNavigationHomeToNavigationHeroInfo2(
                        item.hero_id,
                        item.hero_name,
                        item.hero_status,
                        item.hero_species,
                        item.hero_type,
                        item.hero_gender,
                        item.hero_origin_name,
                        item.hero_location_name,
                        item.hero_created,
                        item.hero_image
                    )
                    Navigation.findNavController(view).navigate(action)
                }
            })

        if (rv_home_heroes.adapter == null) {
            viewState.setAdapter(heroDBAdapter)
        }

        if (heroDBAdapter.loaderIsEmpty())
            heroDBAdapter.setLoader(this)
    }

    fun getRecycler(_recycler: RecyclerView) {
        this.rv_home_heroes = _recycler
    }

    fun resetData() {
        heroDBAdapter.clearData()
        heroPage = 1
        getDataFromAPI(page = heroPage)
    }

    override fun onLoadHero() {
        if (heroPage < maxPages!!) {
            heroPage++
            getDataFromAPI(heroPage)
        }
    }

    // Test
    override fun onOpenLoading() {
        if (heroPage < maxPages!!)
            viewState.openLoading()
    }

    fun setViewNav(view: View) {
        this.view = view
    }
}