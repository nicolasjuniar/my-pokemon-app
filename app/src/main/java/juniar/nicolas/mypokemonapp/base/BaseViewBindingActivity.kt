package juniar.nicolas.mypokemonapp.base

import android.os.Bundle
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.LiveData
import androidx.viewbinding.ViewBinding
import juniar.nicolas.mypokemonapp.ui.common.ProgressBarDialog
import juniar.nicolas.mypokemonapp.util.PokemonPreferenceManager
import juniar.nicolas.mypokemonapp.util.isVisible
import juniar.nicolas.mypokemonapp.util.showToast
import org.koin.android.ext.android.inject

abstract class BaseViewBindingActivity<VB : ViewBinding> : AppCompatActivity() {

    protected val viewBinding: VB by lazy {
        getContentView()
    }

    protected val pokemonPreferenceManager: PokemonPreferenceManager by inject()

    private lateinit var progressBarDialog: ProgressBarDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)
        progressBarDialog = ProgressBarDialog(this)
        onViewReady(savedInstanceState)
    }

    abstract fun getContentView(): VB

    abstract fun onViewReady(savedInstanceState: Bundle?)

    protected fun <T> LiveData<T>.onChangeValue(action: (T) -> Unit) {
        observe(this@BaseViewBindingActivity) { data -> data?.let(action) }
    }

    protected fun observeViewModel(viewModel: BaseViewModel) {
        with(viewModel) {
            observeLoadingViewModel(this)
            observeMessageViewModel(this)
        }
    }


    open fun observeLoadingViewModel(viewModel: BaseViewModel) {
        viewModel.observeLoading().onChangeValue {
            progressBarDialog.isVisible(it)
        }
    }

    open fun observeMessageViewModel(viewModel: BaseViewModel) {
        viewModel.observeMessage().onChangeValue {
            showToast(it)
        }
    }

    fun setupToolbar(
        toolbarId: Toolbar,
        tvTitle: TextView? = null,
        title: String? = null,
        @DrawableRes drawable: Int? = null
    ) {
        setSupportActionBar(toolbarId)
        supportActionBar?.let {
            it.setDisplayShowTitleEnabled(false)
            it.setDisplayHomeAsUpEnabled(
                null != drawable
            )
            it.setDisplayShowHomeEnabled(null != drawable)
            drawable?.let { iconUp ->
                it.setHomeAsUpIndicator(iconUp)
            }
        }
        toolbarId.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
        title?.let {
            tvTitle?.setText(it)
        }
    }
}