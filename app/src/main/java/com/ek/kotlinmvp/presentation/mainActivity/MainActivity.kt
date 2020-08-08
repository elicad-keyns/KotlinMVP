package com.ek.kotlinmvp.presentation.mainActivity

import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.ek.kotlinmvp.R
import kotlinx.android.synthetic.main.activity_main.*
import moxy.MvpAppCompatActivity
import moxy.presenter.InjectPresenter
import java.io.InputStream

class MainActivity : MvpAppCompatActivity(), IMainActivityView {

    //Презентер
    @InjectPresenter
    lateinit var mainActivityPresenter: MainActivityPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Настройка боттом бара
        val navController: NavController = findNavController(R.id.fragment)
        btm_nav_bar.setupWithNavController(navController)

        // Инициализация
        mainActivityPresenter = MainActivityPresenter()
    }
}