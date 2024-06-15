package juniar.nicolas.mypokemonapp.util

import android.app.Activity
import android.content.Intent
import android.os.Bundle

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