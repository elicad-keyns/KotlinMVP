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
class HeroPresenter : MvpPresenter<IHeroView>()