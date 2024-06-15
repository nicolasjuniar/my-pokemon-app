package juniar.nicolas.mypokemonapp.ui.pokemonlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import juniar.nicolas.mypokemonapp.base.BaseViewBindingFragment
import juniar.nicolas.mypokemonapp.databinding.FragmentPokemonListBinding
import juniar.nicolas.mypokemonapp.databinding.ViewholderPokemonBinding
import juniar.nicolas.mypokemonapp.util.DiffCallback
import juniar.nicolas.mypokemonapp.util.GeneralRecyclerViewBindingAdapter

class PokemonListFragment : BaseViewBindingFragment<FragmentPokemonListBinding>() {

    private val pokemonListAdapter by lazy {
        GeneralRecyclerViewBindingAdapter<String, ViewholderPokemonBinding>(
            diffCallback = DiffCallback(),
            holderResBinding = {
                ViewholderPokemonBinding.inflate(LayoutInflater.from(it.context), it, false)
            },
            onBind = { value, binding, _ ->
                binding.tvPokemonName.text = value
            },
            itemListener = { _, _, _ ->
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
            layoutManager = LinearLayoutManager(requireActivity())
        }

        pokemonListAdapter.addData(
            listOf(
                "Bulbasaur",
                "Ivysaur",
                "Venosaur"
            )
        )
    }
}