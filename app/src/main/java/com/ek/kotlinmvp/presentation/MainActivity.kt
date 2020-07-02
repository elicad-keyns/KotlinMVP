package com.ek.kotlinmvp.presentation

import RickAndMorty
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.ek.kotlinmvp.data.local.Planetary
import com.ek.kotlinmvp.presentation.Login.LoginPresenter
import com.ek.kotlinmvp.presentation.Planetary.PlanetaryPresenter
import com.ek.kotlinmvp.R
import com.ek.kotlinmvp.presentation.Login.ILoginView
import com.ek.kotlinmvp.presentation.Planetary.IPlanetaryView
import com.ek.kotlinmvp.presentation.RickAndMorty.IRAMView
import com.ek.kotlinmvp.presentation.RickAndMorty.RAMPresenter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(),
    ILoginView, IPlanetaryView, IRAMView {

    override fun onLoginResult(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private lateinit var loginPresenter: LoginPresenter
    private lateinit var planetaryPresenter: PlanetaryPresenter
    private lateinit var ramPresenter: RAMPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //init
        loginPresenter = LoginPresenter(this)
        planetaryPresenter = PlanetaryPresenter(this)
        ramPresenter = RAMPresenter(this)


        //event
        b_add.setOnClickListener {
            loginPresenter.onLogin(email = et_email.text.toString(), pass = et_pass.text.toString())
            ramPresenter.getDataFromAPI(page = 1)
        }
    }

    override fun onDataCompleteFromApi(planetary: Planetary) {
        Log.d("MainActivity", "------------------------------")
        Log.d("MainActivity", planetary.copyright)
        Log.d("MainActivity", planetary.date)
        Log.d("MainActivity", planetary.explanation)
        Log.d("MainActivity", planetary.hdurl)
        Log.d("MainActivity", planetary.mediaType)
        Log.d("MainActivity", planetary.serviceVersion)
        Log.d("MainActivity", planetary.title)
        Log.d("MainActivity", planetary.url)
        tv_copyright.text = "Copyright: " + planetary.copyright
        tv_date.text = "Date: " + planetary.date
        tv_explanation.text = "Explanation: " + planetary.explanation
        tv_hdurl.text = "Hdurl: " + planetary.hdurl
        tv_media_type.text = "Media Type: " + planetary.mediaType
        tv_service_version.text = "Service Version: " + planetary.serviceVersion
        tv_title.text = "Title: " + planetary.title
        tv_url.text = "Url: " + planetary.url
    }

    override fun onDataErrorFromApi(throwable: Throwable) {
        error("error--------> ${throwable.localizedMessage}")
        Toast.makeText(this, "Ошибка", Toast.LENGTH_SHORT).show()
    }

    override fun onDataCompleteFromAPI(rickAndMorty: RickAndMorty) {
        Log.d("MainActivity", "------------------------------")
        for (element in rickAndMorty.results)
            Log.d("MainActivity", element.toString())
    }

    override fun onDataErrorFromAPI(throwable: Throwable) {
        error("error--------> ${throwable.localizedMessage}")
        Toast.makeText(this, "Ошибка", Toast.LENGTH_SHORT).show()
    }
}