package com.ek.kotlinmvp.presentation.homeFragment

import android.view.View
import com.ek.kotlinmvp.common.LoadStatus
import com.ek.kotlinmvp.data.db.entity.Hero
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface IHomeView: MvpView {

    fun setAdapter(heroDBAdapter: HeroDBAdapter)

    fun isRefresh(loadStatus: LoadStatus)

    fun setRefreshing()

    fun isLoaded()

    fun isLoading()

    fun createAdapter(view: View)

    fun addHeroes(_heroes: ArrayList<Hero>)

    //fun setScrollListener()
}