package juniar.nicolas.mypokemonapp.ui.home

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import juniar.nicolas.mypokemonapp.ui.mypokemon.MyPokemonFragment
import juniar.nicolas.mypokemonapp.ui.pokemonlist.PokemonListFragment

class HomeTabAdapter(fm: FragmentActivity, val context: Context) :
    FragmentStateAdapter(fm) {
    private val fragments =
        listOf<Fragment>(
            PokemonListFragment(),
            MyPokemonFragment()
        )

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }
}