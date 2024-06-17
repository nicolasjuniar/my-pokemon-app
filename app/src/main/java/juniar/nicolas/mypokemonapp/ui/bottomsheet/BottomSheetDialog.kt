package juniar.nicolas.mypokemonapp.ui.bottomsheet

import android.content.Context
import com.google.android.material.bottomsheet.BottomSheetDialog
import juniar.nicolas.mypokemonapp.databinding.BottomSheetDialogBinding

class BottomSheetDialog(
    context: Context,
    title: String,
    desc: String,
    private var onClick: () -> Unit
) :
    BottomSheetDialog(context) {

    val binding: BottomSheetDialogBinding = BottomSheetDialogBinding.inflate(layoutInflater)

    init {
        setContentView(binding.root)
        binding.tvTitle.text = title
        binding.tvDesc.text = desc
        binding.btnNo.setOnClickListener {
            dismiss()
        }
        binding.btnYes.setOnClickListener {
            onClick.invoke()
            dismiss()
        }
    }
}