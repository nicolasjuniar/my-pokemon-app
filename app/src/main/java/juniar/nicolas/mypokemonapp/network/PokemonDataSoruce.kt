package juniar.nicolas.mypokemonapp.network

import juniar.nicolas.mypokemonapp.api.PokemonApi
import juniar.nicolas.mypokemonapp.base.BaseDataSource

class PokemonDataSource(private val pokemonApi: PokemonApi) : BaseDataSource() {
    suspend fun getListPokemon() = suspendDataResult {
        getResult {
            pokemonApi.getListPokemon()
        }
    }

    suspend fun getPokemonDetail(pokedexNumber: Int) = suspendDataResult {
        getResult {
            pokemonApi.getPokemonDetail(pokedexNumber)
        }
    }

    suspend fun getPokemonSpecies(pokedexNumber: Int) = suspendDataResult {
        getResult {
            pokemonApi.getPokemonSpecies(pokedexNumber)
        }
    }
}