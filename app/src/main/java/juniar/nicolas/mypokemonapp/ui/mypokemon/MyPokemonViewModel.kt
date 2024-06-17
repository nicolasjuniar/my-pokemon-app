package juniar.nicolas.mypokemonapp.ui.mypokemon

import androidx.lifecycle.MutableLiveData
import juniar.nicolas.mypokemonapp.base.BaseViewModel
import juniar.nicolas.mypokemonapp.model.MyPokemonModel
import juniar.nicolas.mypokemonapp.util.PokemonPreferenceManager

class MyPokemonViewModel(private val pokemonPreferenceManager: PokemonPreferenceManager) :
    BaseViewModel() {
    private val myPokemon = MutableLiveData<ArrayList<MyPokemonModel>>()

    fun observeMyPokemon() = myPokemon

    fun fetchMyPokemon() {
        myPokemon.postValue(pokemonPreferenceManager.getMyPokemon())
    }
}