package juniar.nicolas.mypokemonapp.response

import com.google.gson.annotations.SerializedName

data class PokemonSpeciesResponse(
    @SerializedName("base_happiness")
    val baseHappiness: Int?,
    @SerializedName("capture_rate")
    val captureRate: Int?,
    @SerializedName("flavor_text_entries")
    val flavorTextEntries: List<FlavorText>?
) {
    fun getFlavorText() = flavorTextEntries?.first()?.flavorText
}

data class FlavorText(
    @SerializedName("flavor_text")
    val flavorText: String?
)