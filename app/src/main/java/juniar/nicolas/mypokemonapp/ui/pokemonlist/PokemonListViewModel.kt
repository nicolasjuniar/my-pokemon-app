package juniar.nicolas.mypokemonapp.ui.pokemonlist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import juniar.nicolas.mypokemonapp.base.BaseViewModel
import juniar.nicolas.mypokemonapp.model.GeneralPokemonModel
import juniar.nicolas.mypokemonapp.network.PokemonRepository
import kotlinx.coroutines.launch

class PokemonListViewModel(private val pokemonRepository: PokemonRepository) : BaseViewModel() {

    private val listPokemon = MutableLiveData<List<GeneralPokemonModel>>()

    fun observeListPokemon() = listPokemon

    fun fetchListPokemon() {
        viewModelScope.launch {
            isLoading.postValue(true)
            pokemonRepository.getListPokemon().onResult({
                listPokemon.postValue(it.results)
            },{
                isError.postValue(true)
            })
        }
    }
}