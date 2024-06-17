package juniar.nicolas.mypokemonapp.response

import com.google.gson.annotations.SerializedName

data class RenamePokemonResponse(
    @SerializedName("new_name")
    val newName: String,
    @SerializedName("additional_name")
    val additionalName: String,
    @SerializedName("counter")
    val counter: Int
)