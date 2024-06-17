package juniar.nicolas.mypokemonapp.ui.pokemonlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.GridLayoutManager
import juniar.nicolas.mypokemonapp.base.BaseViewBindingFragment
import juniar.nicolas.mypokemonapp.databinding.FragmentPokemonListBinding
import juniar.nicolas.mypokemonapp.databinding.ViewholderPokemonBinding
import juniar.nicolas.mypokemonapp.model.GeneralPokemonModel
import juniar.nicolas.mypokemonapp.ui.pokemondetail.PokemonDetailActivity
import juniar.nicolas.mypokemonapp.ui.pokemondetail.PokemonDetailActivity.Companion.PAGE_TYPE
import juniar.nicolas.mypokemonapp.ui.pokemondetail.PokemonDetailActivity.Companion.POKEDEX_NUMBER
import juniar.nicolas.mypokemonapp.ui.pokemondetail.PokemonDetailActivity.Companion.POKEMON_NAME
import juniar.nicolas.mypokemonapp.ui.pokemondetail.PokemonDetailActivity.Companion.RANDOM_POKEMON
import juniar.nicolas.mypokemonapp.ui.pokemondetail.PokemonDetailActivity.Companion.RENAME_COUNTER
import juniar.nicolas.mypokemonapp.util.DiffCallback
import juniar.nicolas.mypokemonapp.util.GeneralRecyclerViewBindingAdapter
import juniar.nicolas.mypokemonapp.util.getPokemonArtwork
import juniar.nicolas.mypokemonapp.util.onLoad
import juniar.nicolas.mypokemonapp.util.openActivity
import juniar.nicolas.mypokemonapp.util.toPokedexNumber
import org.koin.androidx.viewmodel.ext.android.viewModel

class PokemonListFragment : BaseViewBindingFragment<FragmentPokemonListBinding>() {

    private val viewModel: PokemonListViewModel by viewModel()

    private val pokemonListAdapter by lazy {
        GeneralRecyclerViewBindingAdapter<GeneralPokemonModel, ViewholderPokemonBinding>(
            diffCallback = DiffCallback(),
            holderResBinding = {
                ViewholderPokemonBinding.inflate(LayoutInflater.from(it.context), it, false)
            },
            onBind = { value, binding, index ->
                binding.tvPokemonNumber.text = (index + 1).toPokedexNumber()
                binding.tvPokemonName.text = value.name
                binding.pokemonSprite.onLoad(
                    requireActivity(),
                    getPokemonArtwork(index + 1)
                )
            },
            itemListener = { value, index, _ ->
                requireActivity().openActivity<PokemonDetailActivity>(
                    bundleOf(
                        POKEDEX_NUMBER to index + 1,
                        POKEMON_NAME to value.name,
                        PAGE_TYPE to RANDOM_POKEMON
                    )
                )
            }
        )
    }

    override fun getContentView(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentPokemonListBinding.inflate(layoutInflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(viewBinding.rvPokemonList) {
            adapter = pokemonListAdapter
            layoutManager = GridLayoutManager(requireActivity(), 3)
        }
        observeData()
        viewModel.fetchListPokemon()
    }

    private fun observeData() {
        with(viewModel) {
            observeViewModel(this)
            observeListPokemon().onChangeValue {
                pokemonListAdapter.setData(it)
            }
        }
    }
}