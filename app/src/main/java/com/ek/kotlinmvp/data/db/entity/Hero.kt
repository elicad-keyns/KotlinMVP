package com.ek.kotlinmvp.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Hero(
    @PrimaryKey(autoGenerate = false)
    val hero_id : String,
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