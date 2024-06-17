package juniar.nicolas.mypokemonapp.response

import com.google.gson.annotations.SerializedName

data class ReleasePokemonResponse(
    @SerializedName("random_number")
    val randomNumber: Int,
    @SerializedName("is_prime")
    val isPrime: Boolean
)