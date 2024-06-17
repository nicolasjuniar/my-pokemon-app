package juniar.nicolas.mypokemonapp.inject

import juniar.nicolas.mypokemonapp.api.LocalApi
import juniar.nicolas.mypokemonapp.api.PokemonApi
import juniar.nicolas.mypokemonapp.network.LocalDataSource
import juniar.nicolas.mypokemonapp.network.LocalRepository
import juniar.nicolas.mypokemonapp.network.PokemonDataSource
import juniar.nicolas.mypokemonapp.network.PokemonRepository
import juniar.nicolas.mypokemonapp.util.Constant.Companion.NAMED_LOCAL_RETROFIT
import juniar.nicolas.mypokemonapp.util.Constant.Companion.NAMED_POKEAPI_RETROFIT
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

val repositoryModule = module {
    single {
        val apiService = get<Retrofit>(named(NAMED_POKEAPI_RETROFIT)).create(PokemonApi::class.java)
        val remoteDataSource = PokemonDataSource(apiService)
        PokemonRepository(remoteDataSource)
    }

    single {
        val apiService = get<Retrofit>(named(NAMED_LOCAL_RETROFIT)).create(LocalApi::class.java)
        val remoteDataSource = LocalDataSource(apiService)
        LocalRepository(remoteDataSource)
    }
}