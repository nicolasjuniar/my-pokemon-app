package juniar.nicolas.mypokemonapp.ui.pokemondetail

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import juniar.nicolas.mypokemonapp.R
import juniar.nicolas.mypokemonapp.base.BaseViewBindingActivity
import juniar.nicolas.mypokemonapp.databinding.ActivityPokemonDetailBinding
import juniar.nicolas.mypokemonapp.databinding.ViewholderAbilityBinding
import juniar.nicolas.mypokemonapp.databinding.ViewholderStatsBinding
import juniar.nicolas.mypokemonapp.model.MyPokemonModel
import juniar.nicolas.mypokemonapp.request.RenamePokemonRequest
import juniar.nicolas.mypokemonapp.response.Ability
import juniar.nicolas.mypokemonapp.response.PokemonDetailResponse
import juniar.nicolas.mypokemonapp.response.PokemonSpeciesResponse
import juniar.nicolas.mypokemonapp.response.Stat
import juniar.nicolas.mypokemonapp.ui.bottomsheet.BottomSheetCaptured
import juniar.nicolas.mypokemonapp.ui.bottomsheet.BottomSheetDialog
import juniar.nicolas.mypokemonapp.ui.bottomsheet.BottomSheetInfo
import juniar.nicolas.mypokemonapp.ui.bottomsheet.BottomSheetRename
import juniar.nicolas.mypokemonapp.util.Constant.Companion.CAPTURED
import juniar.nicolas.mypokemonapp.util.DiffCallback
import juniar.nicolas.mypokemonapp.util.GeneralRecyclerViewBindingAdapter
import juniar.nicolas.mypokemonapp.util.getDrawableCompat
import juniar.nicolas.mypokemonapp.util.getPokemonArtwork
import juniar.nicolas.mypokemonapp.util.onLoad
import juniar.nicolas.mypokemonapp.util.showToast
import juniar.nicolas.mypokemonapp.util.toPokedexNumber
import juniar.nicolas.mypokemonapp.util.visible
import org.koin.androidx.viewmodel.ext.android.viewModel

class PokemonDetailActivity : BaseViewBindingActivity<ActivityPokemonDetailBinding>() {

    companion object {
        const val POKEDEX_NUMBER = "POKEDEX_NUMBER"
        const val POKEMON_NAME = "POKEMON_NAME"
        const val ADDITIONAL_NAME = "ADDITIONAL_NAME"
        const val POKEMON_NUMBER = "POKEMON_NUMBER"
        const val RANDOM_POKEMON = "RANDOM_POKEMON"
        const val RENAME_COUNTER = "RENAME_COUNTER"
        const val MY_POKEMON = "MY_POKEMON"
        const val PAGE_TYPE = "PAGE_TYPE"
    }

    private val abilitiesAdapter by lazy {
        GeneralRecyclerViewBindingAdapter<Ability, ViewholderAbilityBinding>(
            diffCallback = DiffCallback(),
            holderResBinding = {
                ViewholderAbilityBinding.inflate(LayoutInflater.from(it.context), it, false)
            },
            onBind = { value, binding, _ ->
                binding.tvAbility.text =
                    value.ability?.name + if (value.isHidden == true) " (Hidden)" else ""
            }
        )
    }

    private val statsAdapter by lazy {
        GeneralRecyclerViewBindingAdapter<Stat, ViewholderStatsBinding>(
            diffCallback = DiffCallback(),
            holderResBinding = {
                ViewholderStatsBinding.inflate(LayoutInflater.from(it.context), it, false)
            },
            onBind = { value, binding, _ ->
                binding.tvStatName.text = value.getStatName()
                binding.tvStatValue.text = value.getStatValue().toString()
            }
        )
    }

    private val viewModel: PokemonDetailViewModel by viewModel()

    private val pokedexNumber by lazy {
        intent.getIntExtra(POKEDEX_NUMBER, 0)
    }

    private val pokemonName by lazy {
        intent.getStringExtra(POKEMON_NAME)
    }

    private val pokemonNumber by lazy {
        intent.getIntExtra(POKEMON_NUMBER, -1)
    }

    private val renameCounter by lazy {
        intent.getIntExtra(RENAME_COUNTER, -1)
    }

    override fun getContentView() = ActivityPokemonDetailBinding.inflate(layoutInflater)

    override fun onViewReady(savedInstanceState: Bundle?) {
        setupToolbar(
            viewBinding.toolbar.toolbar,
            viewBinding.toolbar.toolbarTitleBlack,
            pokemonName,
            R.drawable.ic_back
        )
        observeData()
        viewModel.getPokemonDetail(pokedexNumber)
        viewModel.getPokemonSpecies(pokedexNumber)
        viewModel.getCatchRate()
        if (intent.getStringExtra(PAGE_TYPE) == RANDOM_POKEMON) {
            viewBinding.btnCapture.visible()
        } else {
            setupMyPokemonPage()
        }
    }

    private fun setupMyPokemonPage() {
        viewBinding.btnRename.visible()
        viewBinding.btnRelease.visible()
        viewBinding.btnRename.setOnClickListener {
            BottomSheetRename(
                this@PokemonDetailActivity,
                pokemonName.orEmpty()
            ) {
                viewModel.renamePokemon(
                    RenamePokemonRequest(it, renameCounter),
                    pokemonNumber
                )
            }.show()
        }
        viewBinding.btnRelease.setOnClickListener {
            BottomSheetDialog(
                this@PokemonDetailActivity,
                "Release Pokemon",
                "Release this Pokemon?"
            ) {
                viewModel.releasePokemon(pokemonNumber)
            }.show()
        }
    }

    private fun observeData() {
        with(viewModel) {
            observeViewModel(this)
            observePokemonDetail().onChangeValue {
                setPokemonDetail(it)
            }
            observePokemonSpecies().onChangeValue {
                setPokemonSpecies(it)
            }
            observeCatchRate().onChangeValue { catchRate ->
                viewBinding.btnCapture.setOnClickListener {
                    BottomSheetDialog(
                        this@PokemonDetailActivity,
                        "Capture Pokemon",
                        "This Pokemon have ${catchRate * 100}% catch rate, are you sure want to catch it?"
                    ) {
                        processCapturePokemon(catchRate)
                    }.show()
                }
            }
            observeReleasePokemon().onChangeValue {
                if (it.isPrime) {
                    BottomSheetInfo(
                        this@PokemonDetailActivity,
                        "$pokemonName was released.",
                        "Your random release number is ${it.randomNumber} and it's prime number"
                    ) {
                        finish()
                    }.show()
                } else {
                    BottomSheetInfo(
                        this@PokemonDetailActivity,
                        "Release failed",
                        "Your random release number is ${it.randomNumber} and it's not prime number"
                    ).show()
                }
            }
            observeError().onChangeValue {
                if (it) {
                    showToast("Local Backend Service is Off, you can't capture any pokemon!")
                }
            }
            observeSuccess().onChangeValue {
                if (it) {
                    BottomSheetInfo(
                        this@PokemonDetailActivity,
                        "Nickname Changed"
                    ) {
                        finish()
                    }.show()
                }
            }
        }
    }

    private fun processCapturePokemon(catchRate: Float) {
        if (viewModel.calculateCapture(catchRate)) {
            BottomSheetCaptured(this@PokemonDetailActivity, pokemonName.orEmpty()) {
                viewModel.capturePokemon(
                    MyPokemonModel(
                        pokemonPreferenceManager.catchCounter,
                        it,
                        pokemonName.orEmpty(),
                        "",
                        pokedexNumber,
                        -1,
                        CAPTURED
                    )
                )
            }.show()
        } else {
            BottomSheetInfo(this@PokemonDetailActivity, "$pokemonName Fled!", "").show()
        }
    }

    private fun setPokemonDetail(pokemonDetailResponse: PokemonDetailResponse) {
        with(viewBinding) {
            pokemonSprite.onLoad(
                this@PokemonDetailActivity,
                getPokemonArtwork(pokedexNumber)
            )
            tvPokemonNumber.text = pokedexNumber.toPokedexNumber()
            tvPokemonSpecies.text = pokemonDetailResponse.name
            tvPokemonType1.visible()
            tvPokemonType1.handleType(pokemonDetailResponse.getFirstTypeName())
            pokemonDetailResponse.getSecondTypeName()?.let {
                tvPokemonType2.handleType(it)
                tvPokemonType2.visible()
            }
            rvAbility.adapter = abilitiesAdapter
            rvAbility.layoutManager = LinearLayoutManager(this@PokemonDetailActivity)
            pokemonDetailResponse.abilities?.let {
                abilitiesAdapter.setData(it)
            }
            rvStats.adapter = statsAdapter
            rvStats.layoutManager = LinearLayoutManager(this@PokemonDetailActivity)
            pokemonDetailResponse.stats?.let {
                statsAdapter.setData(it)
            }
        }
    }

    private fun setPokemonSpecies(pokemonSpeciesResponse: PokemonSpeciesResponse) {
        with(viewBinding) {
            tvFlavorText.text =
                pokemonSpeciesResponse.getFlavorText()?.replace(Regex("[\n\\f]"), " ")
        }
    }

    fun TextView.handleType(typeName: String) {
        this.text = typeName
        this.background = getDrawableCompat(
            when (typeName.lowercase()) {
                "normal" -> R.drawable.bg_normal
                "fighting" -> R.drawable.bg_fighting
                "flying" -> R.drawable.bg_flying
                "poison" -> R.drawable.bg_poison
                "ground" -> R.drawable.bg_ground
                "rock" -> R.drawable.bg_rock
                "bug" -> R.drawable.bg_bug
                "ghost" -> R.drawable.bg_ghost
                "steel" -> R.drawable.bg_steel
                "fire" -> R.drawable.bg_fire
                "water" -> R.drawable.bg_water
                "grass" -> R.drawable.bg_grass
                "electric" -> R.drawable.bg_electric
                "psychic" -> R.drawable.bg_psychic
                "ice" -> R.drawable.bg_ice
                "dragon" -> R.drawable.bg_dragon
                "dark" -> R.drawable.bg_dark
                else -> R.drawable.bg_fairy
            }
        )
    }
}