package com.ek.kotlinmvp.data.local.rickAndMorty

import com.google.gson.annotations.SerializedName

data class RickAndMorty (
	@SerializedName("info") val info : Info,
	@SerializedName("results") val results : ArrayList<Results>
)