package juniar.nicolas.mypokemonapp.request

import com.google.gson.annotations.SerializedName

data class RenamePokemonRequest(
    @SerializedName("name")
    val name: String,
    @SerializedName("counter")
    val counter: Int
)