package com.ek.kotlinmvp.presentation.heroFragment

import android.content.Context
import android.widget.Toast
import com.ek.kotlinmvp.data.db.HeroDatabase
import com.ek.kotlinmvp.data.db.dao.HeroDao
import com.ek.kotlinmvp.data.db.entity.Hero
import com.ek.kotlinmvp.data.net.RickAndMorty
import com.ek.kotlinmvp.environment.Factory
import moxy.InjectViewState
import moxy.MvpPresenter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@InjectViewState
class HeroPresenter : MvpPresenter<IHeroView>() {

    lateinit var context: Context

    // текущая страница
    var page: Int = 1

    // Максимальное кол-во страниц
    var maxPages: Int? = null

    // Первый запуск вьюшки
    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        getDataFromAPI(page = page)
    }

    // Метод для переключения страницы вперед
    fun pageNext() {
        if ((maxPages != null) && (page < maxPages!!)) {
            page++
            getDataFromAPI(page = page)
        }
    }

    // Метод для переключения страницы назад
    fun pagePrev() {
        if (page > 1) {
            page--
            getDataFromAPI(page = page)
        }
    }

    fun recordData(rickAndMorty: RickAndMorty) {
        val db: HeroDatabase? = HeroDatabase.getHeroDatabase()
        val heroDao: HeroDao? = db?.heroDao()

        maxPages = rickAndMorty.info.pages

        for (result in rickAndMorty.results) {
            val hero = Hero(
                hero_id = result.id.toString(),
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
        Factory.create()
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

//    fun networkRegister() {
//        networkMonitor.register()
//    }
//
//    fun networkUnregister() {
//        networkMonitor.unregister()
//    }

//    private fun checkConnection(context: Context) {
//        // Создание класса для проверки соединения
//        networkMonitor = NetworkMonitorUtil(context = context)
//
//        networkMonitor.result = { isAvailable, type ->
//            when (isAvailable) {
//                true -> {
//                    viewState.hideConnectFail()
//                    viewState.openLoading()
//                    getDataFromAPI(page = page)
//                    //region ConnectionTypes
//                    //when (type) {
//                    //    ConnectionType.Wifi -> {
//                    //        getDataFromAPI(page = page)
//                    //    }
//                    //    ConnectionType.Cellular -> {
//                    //        getDataFromAPI(page = page)
//                    //    }
//                    //}
//                    //endregion
//                }
//                false -> {
//                    Toast.makeText(context, "Нет соединения", Toast.LENGTH_SHORT).show()
//                    viewState.hideLoading()
//                    viewState.openConnectFail()
//                    getDataFromAPI(page = page)
//                }
//            }
//        }
//    }
}