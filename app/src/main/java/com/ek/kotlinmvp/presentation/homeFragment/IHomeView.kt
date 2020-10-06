package com.ek.kotlinmvp.presentation.homeFragment

import android.view.View
import com.ek.kotlinmvp.common.LoadStatus
import com.ek.kotlinmvp.data.db.entity.Hero
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.AddToEndStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndStrategy::class)
interface IHomeView: MvpView {

    fun setAdapter(heroDBAdapter: HeroAdapter)

    fun isRefresh(loadStatus: LoadStatus)

     fun setRefreshing()

    fun createAdapter(view: View)

    fun setHeroesToList(_heroes: ArrayList<Hero?>)

    fun insertLoader()
}