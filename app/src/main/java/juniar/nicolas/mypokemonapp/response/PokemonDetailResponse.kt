package juniar.nicolas.mypokemonapp.response

import com.google.gson.annotations.SerializedName


data class PokemonDetailResponse(
    @SerializedName("abilities")
    val abilities: List<Ability>?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("stats")
    val stats: List<Stat>?,
    @SerializedName("types")
    val types: List<Type>?,
    @SerializedName("weight")
    val weight: Int?
) {
    fun getFirstTypeName() = types?.first()?.type?.name ?: ""
    fun getSecondTypeName() = types?.getOrNull(1)?.type?.name
}

data class Ability(
    @SerializedName("ability")
    val ability: AbilityName?,
    @SerializedName("is_hidden")
    val isHidden: Boolean?,
    @SerializedName("slot")
    val slot: Int?
)

data class Stat(
    @SerializedName("base_stat")
    val baseStat: Int?,
    @SerializedName("effort")
    val effort: Int?,
    @SerializedName("stat")
    val stat: StatName?
) {
    fun getStatName() = stat?.name
    fun getStatValue() = baseStat
}

data class DetailedStat(
    val statName: String,
    val statValue: Float,
    val highestStat: Float
)

data class Type(
    @SerializedName("slot")
    val slot: Int?,
    @SerializedName("type")
    val type: TypeName?
)

data class AbilityName(
    @SerializedName("name")
    val name: String?,
    @SerializedName("url")
    val url: String?
)

data class StatName(
    @SerializedName("name")
    val name: String?,
    @SerializedName("url")
    val url: String?
)

data class TypeName(
    @SerializedName("name")
    val name: String?,
    @SerializedName("url")
    val url: String?
)