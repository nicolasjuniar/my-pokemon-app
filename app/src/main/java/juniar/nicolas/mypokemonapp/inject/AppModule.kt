package juniar.nicolas.mypokemonapp.inject

import juniar.nicolas.mypokemonapp.util.DiffCallback
import org.koin.dsl.module

val AppModules = module {
    single { DiffCallback() }
}