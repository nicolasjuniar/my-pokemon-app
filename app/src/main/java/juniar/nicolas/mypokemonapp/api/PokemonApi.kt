package juniar.nicolas.mypokemonapp.api

import juniar.nicolas.mypokemonapp.response.ListPokemonResponse
import juniar.nicolas.mypokemonapp.response.PokemonDetailResponse
import juniar.nicolas.mypokemonapp.response.PokemonSpeciesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface PokemonApi {

    @GET("pokemon?limit=100000&offset=0")
    suspend fun getListPokemon(): Response<ListPokemonResponse>

    @GET("pokemon/{pokedex-number}")
    suspend fun getPokemonDetail(@Path("pokedex-number") pokedexNumber: Int): Response<PokemonDetailResponse>

    @GET("pokemon-species/{pokedex-number}")
    suspend fun getPokemonSpecies(@Path("pokedex-number") pokedexNumber: Int): Response<PokemonSpeciesResponse>
}