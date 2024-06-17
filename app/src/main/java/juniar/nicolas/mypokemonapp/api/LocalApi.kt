package juniar.nicolas.mypokemonapp.api

import juniar.nicolas.mypokemonapp.request.RenamePokemonRequest
import juniar.nicolas.mypokemonapp.response.GetCatchRateResponse
import juniar.nicolas.mypokemonapp.response.ReleasePokemonResponse
import juniar.nicolas.mypokemonapp.response.RenamePokemonResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface LocalApi {

    @GET("catch-rate")
    suspend fun getCatchRate(): Response<GetCatchRateResponse>

    @GET("release-pokemon")
    suspend fun releasePokemon(): Response<ReleasePokemonResponse>

    @POST("rename-pokemon")
    suspend fun renamePokemon(
        @Body renamePokemonRequest: RenamePokemonRequest
    ): Response<RenamePokemonResponse>
}