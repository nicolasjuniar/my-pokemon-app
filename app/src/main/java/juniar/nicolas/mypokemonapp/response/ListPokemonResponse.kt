package juniar.nicolas.mypokemonapp.response

import com.google.gson.annotations.SerializedName
import juniar.nicolas.mypokemonapp.model.GeneralPokemonModel

data class ListPokemonResponse(
    @SerializedName("results")
    val results: List<GeneralPokemonModel>
)