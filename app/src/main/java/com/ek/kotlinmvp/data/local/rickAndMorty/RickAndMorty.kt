package com.ek.kotlinmvp.data.local.rickAndMorty

import com.ek.kotlinmvp.data.local.rickAndMorty.Info
import com.ek.kotlinmvp.data.local.rickAndMorty.Results
import com.google.gson.annotations.SerializedName

data class RickAndMorty (

	@SerializedName("info") val info : Info,
	@SerializedName("results") val results : List<Results>
)