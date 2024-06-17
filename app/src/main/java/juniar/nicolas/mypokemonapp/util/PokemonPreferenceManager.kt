package juniar.nicolas.mypokemonapp.util

import android.content.SharedPreferences
import com.google.gson.Gson
import juniar.nicolas.mypokemonapp.model.MyPokemonModel
import juniar.nicolas.mypokemonapp.util.Constant.Companion.CATCH_COUNTER
import juniar.nicolas.mypokemonapp.util.Constant.Companion.MY_POKEMON

class PokemonPreferenceManager(sharedPreference: SharedPreferences) {
    var myPokemon: String by PokemonSharedPreference(sharedPreference, MY_POKEMON, "")
    var catchCounter: Int by PokemonSharedPreference(sharedPreference, CATCH_COUNTER, 0)

    fun getMyPokemon() = if (myPokemon.isNotEmpty()) {
        Gson().fromJson<ArrayList<MyPokemonModel>>(myPokemon)
    } else {
        arrayListOf()
    }

    fun increaseCatchCounter() {
        catchCounter += 1
    }
}