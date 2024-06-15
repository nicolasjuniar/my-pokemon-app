package juniar.nicolas.mypokemonapp.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class BaseViewBindingActivity<VB : ViewBinding> : AppCompatActivity() {

    protected val viewBinding: VB by lazy {
        getContentView()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)
        initLayout(savedInstanceState)
        onViewReady(savedInstanceState)
    }

    abstract fun getContentView(): VB

    abstract fun onViewReady(savedInstanceState: Bundle?)

    abstract fun initLayout(savedInstanceState: Bundle?)
}