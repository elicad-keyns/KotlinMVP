package com.ek.kotlinmvp.other

import android.app.Application
import android.content.Context

class MainApplication: Application() {

    companion object {
        lateinit var context: Context
            private set
    }

    override fun onCreate() {
        super.onCreate()
        context = this
    }
}