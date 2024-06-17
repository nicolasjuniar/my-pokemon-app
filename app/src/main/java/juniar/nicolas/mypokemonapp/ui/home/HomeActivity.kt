package juniar.nicolas.mypokemonapp.ui.home

import android.os.Bundle
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import juniar.nicolas.mypokemonapp.base.BaseViewBindingActivity
import juniar.nicolas.mypokemonapp.databinding.ActivityHomeBinding
import juniar.nicolas.mypokemonapp.ui.mypokemon.MyPokemonFragment

class HomeActivity : BaseViewBindingActivity<ActivityHomeBinding>() {

    companion object {
        const val POKEMON_LIST = 0
        const val MY_POKEMON = 1
        const val MY_POKEMON_TAG = "f1"
    }

    private val homeTabAdapter by lazy {
        HomeTabAdapter(
            this@HomeActivity,
            applicationContext,
        )
    }

    override fun getContentView() = ActivityHomeBinding.inflate(layoutInflater)

    override fun onViewReady(savedInstanceState: Bundle?) {
        setupToolbar(
            viewBinding.toolbar.toolbar,
            viewBinding.toolbar.toolbarTitle,
            "Home"
        )
        viewBinding.viewpagerHome.adapter = homeTabAdapter
        TabLayoutMediator(
            viewBinding.tabHome,
            viewBinding.viewpagerHome
        ) { tab, position ->
            tab.text = if (position == 0) {
                "Pokemon List"
            } else {
                "My Pokemon"
            }
        }.attach()
    }
}