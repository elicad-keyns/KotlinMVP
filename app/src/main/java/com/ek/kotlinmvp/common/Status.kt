package com.ek.kotlinmvp.common

import androidx.core.content.ContextCompat
import com.ek.kotlinmvp.R
import com.ek.kotlinmvp.other.MainApplication

enum class Status {
    Alive, Dead, unknown
}

enum class StatusColor(val color: Int) {
    AliveColor( ContextCompat.getColor(MainApplication.context, R.color.colorAccent) ),
    DeadColor ( ContextCompat.getColor(MainApplication.context, R.color.colorVinous) ),
    UnknownColor ( ContextCompat.getColor(MainApplication.context, R.color.colorGray) )
}

enum class LoadStatus{
    On,
    Off
}