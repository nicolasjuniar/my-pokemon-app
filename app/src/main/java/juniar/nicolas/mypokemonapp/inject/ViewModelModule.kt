package juniar.nicolas.mypokemonapp.inject

import juniar.nicolas.mypokemonapp.ui.mypokemon.MyPokemonViewModel
import juniar.nicolas.mypokemonapp.ui.pokemondetail.PokemonDetailViewModel
import juniar.nicolas.mypokemonapp.ui.pokemonlist.PokemonListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { PokemonListViewModel(get()) }
    viewModel { PokemonDetailViewModel(get(), get(), get()) }
    viewModel { MyPokemonViewModel(get()) }
}