package juniar.nicolas.mypokemonapp.ui.home

import android.os.Bundle
import com.google.android.material.tabs.TabLayoutMediator
import juniar.nicolas.mypokemonapp.base.BaseViewBindingActivity
import juniar.nicolas.mypokemonapp.databinding.ActivityHomeBinding

class HomeActivity : BaseViewBindingActivity<ActivityHomeBinding>() {

    private val homeTabAdapter by lazy {
        HomeTabAdapter(
            this@HomeActivity,
            applicationContext,
        )
    }

    override fun getContentView()= ActivityHomeBinding.inflate(layoutInflater)

    override fun initLayout(savedInstanceState: Bundle?) {
    }

    override fun onViewReady(savedInstanceState: Bundle?) {
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