package com.ek.kotlinmvp.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ek.kotlinmvp.data.local.rickAndMorty.Location
import com.ek.kotlinmvp.data.local.rickAndMorty.Origin
import com.ek.kotlinmvp.data.local.rickAndMorty.RickAndMorty
import com.google.gson.annotations.SerializedName

@Entity
data class Hero(
    @PrimaryKey(autoGenerate = false)
    val hero_id : Int,
    val hero_name : String,
    val hero_status : String,
    val hero_species : String,
    val hero_type : String,
    val hero_gender : String,
    val hero_origin_name : String,
    val hero_location_name : String,
    val hero_image : String,
    val hero_created : String,
    val hero_page: Int,
    val hero_max_pages: Int?
)