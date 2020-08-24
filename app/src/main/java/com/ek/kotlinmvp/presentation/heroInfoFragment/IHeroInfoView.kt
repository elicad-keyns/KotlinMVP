package com.ek.kotlinmvp.presentation.heroInfoFragment

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType


@StateStrategyType(AddToEndSingleStrategy::class)
interface IHeroInfoView: MvpView