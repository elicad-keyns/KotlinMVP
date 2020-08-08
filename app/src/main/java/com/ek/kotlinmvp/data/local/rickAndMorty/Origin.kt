package com.ek.kotlinmvp.data.local.rickAndMorty

import com.google.gson.annotations.SerializedName

data class Origin (

	@SerializedName("name") val name : String,
	@SerializedName("url") val url : String
)