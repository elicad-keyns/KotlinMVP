package com.ek.kotlinmvp.presentation.mainActivity

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.ek.kotlinmvp.R
import com.ek.kotlinmvp.other.MainApplication
import kotlinx.android.synthetic.main.activity_main.*
import moxy.MvpAppCompatActivity
import moxy.presenter.InjectPresenter

class MainActivity : MvpAppCompatActivity(R.layout.activity_main), IMainActivityView {

    //Презентер
    @InjectPresenter
    lateinit var mainActivityPresenter: MainActivityPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Настройка боттом бара
        val navController: NavController = findNavController(R.id.fragment)
        btmNavBar.setupWithNavController(navController)
    }




}