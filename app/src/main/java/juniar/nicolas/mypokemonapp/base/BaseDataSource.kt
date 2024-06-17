package juniar.nicolas.mypokemonapp.base

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

abstract class BaseDataSource {
    fun <T> errorState(
        msg: String
    ): ResourceState<ResponseWrapper<T>> {
        return ResourceState.Error(
            ResponseWrapper(
                null,
                msg
            )
        )
    }

    suspend fun <T> suspendDataResult(request: suspend () -> ResourceState<T>): ResourceState<T> {
        return withContext(Dispatchers.IO) {
            request.invoke()
        }
    }

    suspend fun <T> getResult(
        request: suspend () ->
        Response<T>
    ): ResourceState<ResponseWrapper<T>> {
        return try {
            val response = request()
            val body = response.body()
            if (!response.isSuccessful || body == null) {
                val message = response.errorBody()?.string()
                return errorState(message ?: "")
            }

            return ResourceState.Success(
                ResponseWrapper(
                    body,
                    null
                )
            )
        } catch (e: Exception) {
            errorState(msg = e.message ?: e.toString())
        }
    }
}