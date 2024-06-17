package juniar.nicolas.mypokemonapp.ui.bottomsheet

import android.content.Context
import com.google.android.material.bottomsheet.BottomSheetDialog
import juniar.nicolas.mypokemonapp.databinding.BottomSheetInfoBinding
import juniar.nicolas.mypokemonapp.util.visible

class BottomSheetInfo(
    context: Context,
    title: String,
    desc: String = "",
    onClick: (() -> Unit)? = null
) :
    BottomSheetDialog(context) {
    private val binding: BottomSheetInfoBinding = BottomSheetInfoBinding.inflate(layoutInflater)

    init {
        setContentView(binding.root)
        binding.tvTitle.text = title
        if (desc.isNotEmpty()) {
            binding.tvDesc.text = desc
            binding.tvDesc.visible()
        }
        binding.btnOk.setOnClickListener {
            onClick?.invoke()
            dismiss()
        }
    }
}