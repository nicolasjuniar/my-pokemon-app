package juniar.nicolas.mypokemonapp.util

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.appcompat.content.res.AppCompatResources
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

inline fun <reified T : Activity> Activity.openActivity(
    bundle: Bundle? = null,
    isFinishAffinity: Boolean = false,
    isFinish: Boolean = false,
    isClearTopSingleTop: Boolean = false
) {
    val intent = Intent(this, T::class.java)
    if (isClearTopSingleTop) {
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
    }
    if (bundle != null) {
        intent.putExtras(bundle)
    }
    startActivity(intent)
    if (isFinish) {
        finish()
    }
    if (isFinishAffinity) {
        finishAffinity()
    }
}

fun ImageView.onLoad(
    context: Context,
    url: String
) {
    Glide.with(context)
        .load(url)
        .into(this)
}

fun Context.showToast(text: Any) {
    Toast.makeText(this, text.toString(), Toast.LENGTH_SHORT)
        .show()
}

fun Dialog.isVisible(visible: Boolean) {
    if (visible) {
        this.show()
    } else {
        this.dismiss()
    }
}

fun Context.getDrawableCompat(@DrawableRes drawableId: Int) =
    AppCompatResources.getDrawable(this, drawableId)

fun View.gone() {
    this.visibility = View.GONE
}

fun View.visible() {
    this.visibility = View.VISIBLE
    this.isEnabled = true
}

fun Int?.orEmpty() = this ?: 0

inline fun <reified T> Gson.fromJson(json: String): T =
    this.fromJson<T>(json, object : TypeToken<T>() {}.type)

fun Any.encodeJson() = Gson().toJson(this)

fun getPokemonArtwork(pokedexNumber: Int) =
    "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${pokedexNumber}.png"

fun Int.toPokedexNumber(): String {
    return when (this.toString().length) {
        1 -> "#000$this"
        2 -> "#00$this"
        3 -> "#0$this"
        else -> "#$this"
    }
}