package juniar.nicolas.mypokemonapp.base

sealed class ResourceState<out T> {
    data class Success<out T>(val result: T) : ResourceState<T>()
    data class Error<T>(val error: T) : ResourceState<T>()
}

data class ErrorResponse(val code: Int?, val data: Any?, val message: String?)

data class ResponseWrapper<out T>(
    val data: T?,
    val errorMessage: String?
)