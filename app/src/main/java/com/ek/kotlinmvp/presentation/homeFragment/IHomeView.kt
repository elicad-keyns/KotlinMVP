package com.ek.kotlinmvp.presentation.homeFragment

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface IHomeView: MvpView {
    // Открытие загрузки и закрытие ресайклера
//    fun openLoading()
//    // Закрытие загрузки и открытие ресайклера
//    fun hideLoading()
//
//    // Открыть уведомление о потере соединения
//    fun openConnectFail()
//    // Закрыть уведомление о потере соединения
//    fun hideConnectFail()
//
    fun getDataFromDB(page: Int)
}