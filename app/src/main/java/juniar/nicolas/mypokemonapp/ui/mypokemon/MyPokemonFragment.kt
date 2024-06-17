package juniar.nicolas.mypokemonapp.ui.mypokemon

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import juniar.nicolas.mypokemonapp.base.BaseViewBindingFragment
import juniar.nicolas.mypokemonapp.databinding.FragmentMyPokemonBinding
import juniar.nicolas.mypokemonapp.databinding.ViewholderPokemonBinding
import juniar.nicolas.mypokemonapp.model.MyPokemonModel
import juniar.nicolas.mypokemonapp.ui.pokemondetail.PokemonDetailActivity
import juniar.nicolas.mypokemonapp.ui.pokemondetail.PokemonDetailActivity.Companion.ADDITIONAL_NAME
import juniar.nicolas.mypokemonapp.ui.pokemondetail.PokemonDetailActivity.Companion.MY_POKEMON
import juniar.nicolas.mypokemonapp.ui.pokemondetail.PokemonDetailActivity.Companion.PAGE_TYPE
import juniar.nicolas.mypokemonapp.ui.pokemondetail.PokemonDetailActivity.Companion.POKEDEX_NUMBER
import juniar.nicolas.mypokemonapp.ui.pokemondetail.PokemonDetailActivity.Companion.POKEMON_NAME
import juniar.nicolas.mypokemonapp.ui.pokemondetail.PokemonDetailActivity.Companion.POKEMON_NUMBER
import juniar.nicolas.mypokemonapp.ui.pokemondetail.PokemonDetailActivity.Companion.RENAME_COUNTER
import juniar.nicolas.mypokemonapp.util.Constant.Companion.CAPTURED
import juniar.nicolas.mypokemonapp.util.DiffCallback
import juniar.nicolas.mypokemonapp.util.GeneralRecyclerViewBindingAdapter
import juniar.nicolas.mypokemonapp.util.getPokemonArtwork
import juniar.nicolas.mypokemonapp.util.onLoad
import juniar.nicolas.mypokemonapp.util.openActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class MyPokemonFragment : BaseViewBindingFragment<FragmentMyPokemonBinding>() {

    private val viewModel: MyPokemonViewModel by viewModel()

    private var initialized = false

    private val pokemonListAdapter by lazy {
        GeneralRecyclerViewBindingAdapter<MyPokemonModel, ViewholderPokemonBinding>(
            diffCallback = DiffCallback(),
            holderResBinding = {
                ViewholderPokemonBinding.inflate(LayoutInflater.from(it.context), it, false)
            },
            onBind = { value, binding, _ ->
                binding.tvPokemonNumber.text = value.pokemonSpecies
                binding.tvPokemonName.text = value.pokemonName + value.additionalName
                binding.pokemonSprite.onLoad(
                    requireActivity(),
                    getPokemonArtwork(value.pokedexNumber)
                )
            },
            itemListener = { value, _, _ ->
                requireActivity().openActivity<PokemonDetailActivity>(
                    bundleOf(
                        POKEDEX_NUMBER to value.pokedexNumber,
                        POKEMON_NAME to value.pokemonName,
                        ADDITIONAL_NAME to value.additionalName,
                        POKEMON_NUMBER to value.pokemonNumber,
                        RENAME_COUNTER to value.renameCounter,
                        PAGE_TYPE to MY_POKEMON
                    )
                )
            }
        )
    }

    override fun getContentView(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentMyPokemonBinding.inflate(layoutInflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(viewBinding.rvPokemonList) {
            adapter = pokemonListAdapter
            layoutManager = GridLayoutManager(requireActivity(), 3)
        }
        observeData()
        refresh()
        initialized = true
    }

    private fun refresh() {
        viewModel.fetchMyPokemon()
    }

    private fun observeData() {
        with(viewModel) {
            observeViewModel(this)
            observeMyPokemon().onChangeValue {
                val filteredPokemons = it.filter { myPokemon ->
                    myPokemon.status == CAPTURED
                }
                viewBinding.emptyMessage.isVisible = filteredPokemons.isEmpty()
                viewBinding.rvPokemonList.isVisible = filteredPokemons.isNotEmpty()
                pokemonListAdapter.setData(filteredPokemons)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (initialized) {
            refresh()
        }
    }
}