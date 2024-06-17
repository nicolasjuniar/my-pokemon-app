package juniar.nicolas.mypokemonapp.ui.pokemondetail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import juniar.nicolas.mypokemonapp.base.BaseViewModel
import juniar.nicolas.mypokemonapp.model.MyPokemonModel
import juniar.nicolas.mypokemonapp.network.LocalRepository
import juniar.nicolas.mypokemonapp.network.PokemonRepository
import juniar.nicolas.mypokemonapp.request.RenamePokemonRequest
import juniar.nicolas.mypokemonapp.response.PokemonDetailResponse
import juniar.nicolas.mypokemonapp.response.PokemonSpeciesResponse
import juniar.nicolas.mypokemonapp.response.ReleasePokemonResponse
import juniar.nicolas.mypokemonapp.response.RenamePokemonResponse
import juniar.nicolas.mypokemonapp.util.Constant.Companion.RELEASED
import juniar.nicolas.mypokemonapp.util.PokemonPreferenceManager
import juniar.nicolas.mypokemonapp.util.encodeJson
import kotlinx.coroutines.launch
import kotlin.random.Random

class PokemonDetailViewModel(
    private val pokemonRepository: PokemonRepository,
    private val localRepository: LocalRepository,
    private val pokemonPreferenceManager: PokemonPreferenceManager
) : BaseViewModel() {

    private val pokemonDetail = MutableLiveData<PokemonDetailResponse>()
    private val pokemonSpecies = MutableLiveData<PokemonSpeciesResponse>()
    private val catchRate = MutableLiveData<Float>()
    private val releasePokemon = MutableLiveData<ReleasePokemonResponse>()

    fun observePokemonDetail() = pokemonDetail
    fun observePokemonSpecies() = pokemonSpecies

    fun observeCatchRate() = catchRate

    fun observeReleasePokemon() = releasePokemon

    fun getPokemonDetail(pokedexNumber: Int) {
        viewModelScope.launch {
            isLoading.postValue(true)
            pokemonRepository.getPokemonDetail(pokedexNumber).onResult({
                pokemonDetail.postValue(it)
            }, {
                isError.postValue(true)
            })
        }
    }

    fun getPokemonSpecies(pokedexNumber: Int) {
        viewModelScope.launch {
            isLoading.postValue(true)
            pokemonRepository.getPokemonSpecies(pokedexNumber).onResult({
                pokemonSpecies.postValue(it)
            }, {
                isError.postValue(true)
            })
        }
    }

    fun getCatchRate() {
        viewModelScope.launch {
            isLoading.postValue(true)
            localRepository.getCatchRate().onResult({
                catchRate.postValue(it.catchRate)
            }, {
                isError.postValue(true)
            })
        }
    }

    fun calculateCapture(catchRate: Float) = Random.nextFloat() < catchRate

    fun capturePokemon(myPokemonModel: MyPokemonModel) {
        val listPokemon = pokemonPreferenceManager.getMyPokemon()
        listPokemon.add(myPokemonModel)
        pokemonPreferenceManager.myPokemon = listPokemon.encodeJson()
        pokemonPreferenceManager.increaseCatchCounter()
    }

    fun releasePokemon(pokemonNumber: Int) {
        viewModelScope.launch {
            isLoading.postValue(true)
            localRepository.releasePokemon().onResult({
                releasePokemon.postValue(it)
                if (it.isPrime) {
                    releasePokemonByPokemonNumber(pokemonNumber)
                }
            }, {
                isError.postValue(true)
            })
        }
    }

    private fun releasePokemonByPokemonNumber(pokemonNumber: Int) {
        val listPokemon = pokemonPreferenceManager.getMyPokemon()
        listPokemon.find { it.pokemonNumber == pokemonNumber }?.status = RELEASED
        pokemonPreferenceManager.myPokemon = listPokemon.encodeJson()
    }

    fun renamePokemon(renamePokemonRequest: RenamePokemonRequest, pokemonNumber: Int) {
        viewModelScope.launch {
            isLoading.postValue(true)
            localRepository.renamePokemon(renamePokemonRequest).onResult({
                isSuccess.postValue(true)
                renamePokemonByPokemonNumber(it, pokemonNumber)
            }, {
                isError.postValue(true)
            })
        }
    }

    private fun renamePokemonByPokemonNumber(
        renamePokemonResponse: RenamePokemonResponse,
        pokemonNumber: Int
    ) {
        val listPokemon = pokemonPreferenceManager.getMyPokemon()
        listPokemon.forEach {
            if (it.pokemonNumber == pokemonNumber) {
                it.pokemonName = renamePokemonResponse.newName
                it.additionalName = renamePokemonResponse.additionalName
                it.renameCounter = renamePokemonResponse.counter
            }
        }
        pokemonPreferenceManager.myPokemon = listPokemon.encodeJson()
    }
}