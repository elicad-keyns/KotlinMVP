package com.ek.kotlinmvp.presentation.heroFragment

import android.content.Context
import android.widget.Toast
import com.ek.kotlinmvp.data.local.rickAndMorty.RickAndMorty
import com.ek.kotlinmvp.environment.IRickAndMortyAPI
import com.ek.kotlinmvp.other.NetworkMonitorUtil
import moxy.InjectViewState
import moxy.MvpPresenter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@InjectViewState
class HeroPresenter : MvpPresenter<IHeroView>() {

    lateinit var context: Context

    private lateinit var networkMonitor: NetworkMonitorUtil

    // текущая страница
    var page: Int = 1

    // Максимальное кол-во страниц
    var maxPages: Int? = null

    // Первый запуск вьюшки
    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        checkConnection(context)
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

    fun networkRegister() {
        networkMonitor.register()
    }

    fun networkUnregister() {
        networkMonitor.unregister()
    }

    //region kek
    //endregion

    private fun checkConnection(context: Context) {
        // Создание класса для проверки соединения
        networkMonitor = NetworkMonitorUtil(context = context)

        networkMonitor.result = { isAvailable, type ->
            when (isAvailable) {
                true -> {
                    viewState.hideConnectFail()
                    viewState.openLoading()
                    getDataFromAPI(page = page)
                    //region ConnectionTypes
                    //when (type) {
                    //    ConnectionType.Wifi -> {
                    //        getDataFromAPI(page = page)
                    //    }
                    //    ConnectionType.Cellular -> {
                    //        getDataFromAPI(page = page)
                    //    }
                    //}
                    //endregion
                }
                false -> {
                    Toast.makeText(context, "Нет соединения", Toast.LENGTH_SHORT).show()
                    viewState.hideLoading()
                    viewState.openConnectFail()
                }
            }
        }
    }

    // Старт запроса и получение инфы
    private fun getDataFromAPI(page: Int) {
        IRickAndMortyAPI.create()
            .getCharacterPage(page = page)
            .enqueue(object : Callback<RickAndMorty> {
                override fun onFailure(call: Call<RickAndMorty>, t: Throwable) {
                    viewState.onDataErrorFromAPI(t)
                }

                override fun onResponse(
                    call: Call<RickAndMorty>,
                    response: Response<RickAndMorty>
                ) {
                    viewState.onDataCompleteFromAPI(response.body() as RickAndMorty)
                }
            })
    }
}