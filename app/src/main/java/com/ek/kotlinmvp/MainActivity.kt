package com.ek.kotlinmvp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.ek.kotlinmvp.Model.Planetary
import com.ek.kotlinmvp.Presenter.LoginPresenter
import com.ek.kotlinmvp.Presenter.PlanetaryPresenter
import com.ek.kotlinmvp.View.ILoginView
import com.ek.kotlinmvp.View.IPlanetaryView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), ILoginView, IPlanetaryView {

    override fun onLoginResult(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private lateinit var loginPresenter: LoginPresenter
    private lateinit var planetaryPresenter: PlanetaryPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //init
        loginPresenter = LoginPresenter(this)
        planetaryPresenter = PlanetaryPresenter(this)

        //event
        b_add.setOnClickListener {
            loginPresenter.onLogin(email = et_email.text.toString(), pass = et_pass.text.toString())
            planetaryPresenter.getDataFromApi()
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
}