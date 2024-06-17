package juniar.nicolas.mypokemonapp.model

import com.google.gson.annotations.SerializedName

data class GeneralPokemonModel(
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String
)