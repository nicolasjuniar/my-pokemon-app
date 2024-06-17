package juniar.nicolas.mypokemonapp.network

class PokemonRepository(private val pokemonDataSource: PokemonDataSource) {

    suspend fun getListPokemon() = pokemonDataSource.getListPokemon()

    suspend fun getPokemonDetail(pokedexNumber: Int) =
        pokemonDataSource.getPokemonDetail(pokedexNumber)

    suspend fun getPokemonSpecies(pokedexNumber: Int) =
        pokemonDataSource.getPokemonSpecies(pokedexNumber)
}