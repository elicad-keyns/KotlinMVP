package com.ek.kotlinmvp.data.local.rickAndMorty

import com.ek.kotlinmvp.data.net.Location
import com.ek.kotlinmvp.data.net.Origin
import com.google.gson.annotations.SerializedName

data class LocalResults(
    val localId: Int,
    val localName: String,
    val localStatus: String,
    val localSpecies: String,
    val localType: String,
    val localGender: String,
    val localOrigin: LocalOrigin,
    val localLocation: LocalLocation,
    val localImage: String,
    val localEpisode: List<String>,
    val localUrl: String,
    val localCreated: String
)