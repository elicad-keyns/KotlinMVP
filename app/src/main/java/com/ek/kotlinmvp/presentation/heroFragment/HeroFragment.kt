package com.ek.kotlinmvp.presentation.heroFragment

import com.ek.kotlinmvp.R
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter

class HeroFragment : MvpAppCompatFragment(R.layout.fragment_hero), IHeroView {

    // Презентеры
    @InjectPresenter
    lateinit var heroPresenter: HeroPresenter

}