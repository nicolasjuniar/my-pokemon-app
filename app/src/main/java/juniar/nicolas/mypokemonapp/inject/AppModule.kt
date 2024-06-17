package juniar.nicolas.mypokemonapp.inject

import android.content.Context
import juniar.nicolas.mypokemonapp.util.Constant.Companion.POKEMON_SHARED_PREFERENCE
import juniar.nicolas.mypokemonapp.util.DiffCallback
import juniar.nicolas.mypokemonapp.util.PokemonPreferenceManager
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val AppModules = module {
    single { DiffCallback() }
    single {
        PokemonPreferenceManager(
            androidContext().getSharedPreferences(
                POKEMON_SHARED_PREFERENCE,
                Context.MODE_PRIVATE
            )
        )
    }
}