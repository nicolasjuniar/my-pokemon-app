package juniar.nicolas.mypokemonapp.response

import com.google.gson.annotations.SerializedName

data class GetCatchRateResponse(
    @SerializedName("catch_rate")
    val catchRate: Float
)