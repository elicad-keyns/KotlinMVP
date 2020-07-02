package com.ek.kotlinmvp.data.local

import com.google.gson.annotations.SerializedName

data class Planetary(
    @SerializedName("copyright")
    val copyright: String,
    @SerializedName("date")
    val date: String,
    @SerializedName("explanation")
    val explanation: String,
    @SerializedName("hdurl")
    val hdurl: String,
    @SerializedName("media_type")
    val mediaType: String,
    @SerializedName("service_version")
    val serviceVersion: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("url")
    val url: String
)

/*

{
  "copyright": "Will Godward",
  "date": "2020-06-29",
  "explanation": "When the lake.",
  "hdurl": "https://apod.nasa.gov/apod/image/2006/SkyReflections_Godward_2000.jpg",
  "media_type": "image",
  "service_version": "v1",
  "title": "Dark Sky Reflections",
  "url": "https://apod.nasa.gov/apod/image/2006/SkyReflections_Godward_1080.jpg"
}

 */