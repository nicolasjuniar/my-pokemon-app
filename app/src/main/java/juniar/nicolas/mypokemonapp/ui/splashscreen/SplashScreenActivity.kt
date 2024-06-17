package juniar.nicolas.mypokemonapp.ui.splashscreen

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import juniar.nicolas.mypokemonapp.base.BaseViewBindingActivity
import juniar.nicolas.mypokemonapp.databinding.ActivitySplashScreenBinding
import juniar.nicolas.mypokemonapp.ui.home.HomeActivity
import juniar.nicolas.mypokemonapp.util.openActivity

class SplashScreenActivity : BaseViewBindingActivity<ActivitySplashScreenBinding>() {
    override fun getContentView() = ActivitySplashScreenBinding.inflate(layoutInflater)

    override fun onViewReady(savedInstanceState: Bundle?) {
        Handler(Looper.getMainLooper()).postDelayed({
            openActivity<HomeActivity>()
        }, 2000L)
    }
}