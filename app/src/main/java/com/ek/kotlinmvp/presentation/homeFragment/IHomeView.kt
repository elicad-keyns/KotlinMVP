package com.ek.kotlinmvp.presentation.homeFragment

import com.ek.kotlinmvp.data.db.entity.Hero
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface IHomeView: MvpView {

    fun setAdapter(heroDBAdapter: HeroDBAdapter)

    fun isRefreshed()

    fun isRefreshing()

    fun setRefreshing()

    fun isLoaded()

    fun createAdapter()

    fun addHeroes(_heroes: ArrayList<Hero>)

    fun setScrollListener()
}