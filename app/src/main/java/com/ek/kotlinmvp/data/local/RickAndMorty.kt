import com.google.gson.annotations.SerializedName

data class RickAndMorty (

	@SerializedName("info") val info : Info,
	@SerializedName("results") val results : List<Results>
)