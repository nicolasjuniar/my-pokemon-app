package juniar.nicolas.mypokemonapp.network

import juniar.nicolas.mypokemonapp.request.RenamePokemonRequest

class LocalRepository(private val localDataSource: LocalDataSource) {

    suspend fun getCatchRate() = localDataSource.getCatchRate()

    suspend fun releasePokemon() = localDataSource.releasePokemon()

    suspend fun renamePokemon(renamePokemonRequest: RenamePokemonRequest) =
        localDataSource.renamePokemon(renamePokemonRequest)
}