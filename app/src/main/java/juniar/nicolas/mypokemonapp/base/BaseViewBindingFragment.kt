package juniar.nicolas.mypokemonapp.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import juniar.nicolas.mypokemonapp.util.DiffCallback
import org.koin.android.ext.android.inject

abstract class BaseViewBindingFragment<VB : ViewBinding> : Fragment() {

    protected val diffCallback: DiffCallback by inject()

    private var _viewBinding: VB? = null
    protected val viewBinding get() = _viewBinding!!

    abstract fun getContentView(inflater: LayoutInflater, container: ViewGroup?): VB

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _viewBinding = getContentView(inflater, container)
        return viewBinding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _viewBinding = null
    }
}