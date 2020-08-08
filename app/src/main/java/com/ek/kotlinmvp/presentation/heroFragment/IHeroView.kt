package com.ek.kotlinmvp.presentation.heroFragment

import android.widget.ProgressBar
import com.ek.kotlinmvp.data.local.rickAndMorty.RickAndMorty
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType


@StateStrategyType(AddToEndSingleStrategy::class)
interface IHeroView: MvpView {
    // Если пришла инфа с апи
    fun onDataCompleteFromAPI(rickAndMorty: RickAndMorty)
    // Если с апи не пришла инфа
    fun onDataErrorFromAPI(throwable: Throwable)

    // Метод для присваивания страницы
    fun setPageText()

    // Открытие загрузки и закрытие ресайклера
    fun openLoading()
    // Закрытие загрузки и открытие ресайклера
    fun hideLoading()

    fun openConnectFail()
    fun hideConnectFail()
}