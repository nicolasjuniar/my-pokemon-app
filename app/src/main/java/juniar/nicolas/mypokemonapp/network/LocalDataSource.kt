package juniar.nicolas.mypokemonapp.network

import android.util.Log
import juniar.nicolas.mypokemonapp.api.LocalApi
import juniar.nicolas.mypokemonapp.base.BaseDataSource
import juniar.nicolas.mypokemonapp.request.RenamePokemonRequest
import juniar.nicolas.mypokemonapp.util.encodeJson

class LocalDataSource(private val localApi: LocalApi) : BaseDataSource() {
    suspend fun getCatchRate() = suspendDataResult {
        getResult {
            localApi.getCatchRate()
        }
    }

    suspend fun releasePokemon() = suspendDataResult {
        getResult {
            localApi.releasePokemon()
        }
    }

    suspend fun renamePokemon(renamePokemonRequest: RenamePokemonRequest) = suspendDataResult {
        getResult {
            localApi.renamePokemon(renamePokemonRequest)
        }
    }
}