package juniar.nicolas.mypokemonapp.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel() {
    protected val isError = MutableLiveData<Boolean>()
    protected val isLoading = MutableLiveData<Boolean>()
    protected val isSuccess = MutableLiveData<Boolean>()
    protected val message = MutableLiveData<String>()

    fun observeError(): LiveData<Boolean> = isError
    fun observeLoading(): LiveData<Boolean> = isLoading
    fun observeSuccess(): LiveData<Boolean> = isSuccess
    fun observeMessage(): LiveData<String> = message

    @Suppress("ComplexMethod")
    protected fun <T> StateWrapper<T>.onResult(
        action: (T) -> Unit,
        actionError: (String) -> Unit
    ) {
        when (this@onResult) {
            is ResourceState.Success -> {
                isLoading.postValue(false)
                this@onResult.result.data?.let {
                    action.invoke(it)
                }
            }

            is ResourceState.Error -> {
                actionError.invoke(this@onResult.error.errorMessage ?: "")
                isLoading.postValue(false)
            }
        }
    }
}

typealias StateWrapper<T> = ResourceState<ResponseWrapper<T>>