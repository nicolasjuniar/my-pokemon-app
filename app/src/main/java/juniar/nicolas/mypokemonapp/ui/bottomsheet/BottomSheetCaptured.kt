package juniar.nicolas.mypokemonapp.ui.bottomsheet

import android.content.Context
import androidx.core.widget.addTextChangedListener
import com.google.android.material.bottomsheet.BottomSheetDialog
import juniar.nicolas.mypokemonapp.databinding.BottomSheetCapturedBinding

class BottomSheetCaptured(
    context: Context,
    pokemonName: String,
    onClick: (nickname: String) -> Unit
) : BottomSheetDialog(context) {
    private val binding: BottomSheetCapturedBinding =
        BottomSheetCapturedBinding.inflate(layoutInflater)

    init {
        setContentView(binding.root)
        setCancelable(false)
        binding.tvDesc.text = "$pokemonName was caught!" +
                "\nYou can give nickname this Pokemon" +
                "\nPokemon name will be used if you leave it blank!"
        binding.etNickname.addTextChangedListener {
            binding.tvCounter.text = "${it?.length}/10"
        }
        binding.btnYes.setOnClickListener {
            val nickname = binding.etNickname.text.toString()
            onClick.invoke(
                nickname.ifEmpty {
                    pokemonName
                }
            )
            dismiss()
        }
    }
}