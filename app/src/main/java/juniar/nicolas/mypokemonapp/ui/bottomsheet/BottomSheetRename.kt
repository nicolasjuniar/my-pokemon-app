package juniar.nicolas.mypokemonapp.ui.bottomsheet

import android.content.Context
import androidx.core.widget.addTextChangedListener
import com.google.android.material.bottomsheet.BottomSheetDialog
import juniar.nicolas.mypokemonapp.databinding.BottomSheetCapturedBinding
import juniar.nicolas.mypokemonapp.util.showToast

class BottomSheetRename(
    context: Context,
    pokemonName: String,
    onClick: (nickname: String) -> Unit
) : BottomSheetDialog(context) {
    private val binding: BottomSheetCapturedBinding =
        BottomSheetCapturedBinding.inflate(layoutInflater)

    init {
        setContentView(binding.root)
        setCancelable(false)
        binding.etNickname.addTextChangedListener {
            binding.tvCounter.text = "${it?.length}/10"
        }
        binding.etNickname.setText(pokemonName)
        binding.btnYes.setOnClickListener {
            if (binding.etNickname.text.isEmpty()) {
                context.showToast("Nickname can't be empty!")
            } else {
                onClick.invoke(
                    binding.etNickname.text.toString()
                )
                dismiss()
            }
        }
    }
}