package org.kobjects.komponents.core

import android.content.Context
import android.view.Choreographer
import kotlin.math.ceil

fun Context.pxToPt(px: Int): Double {
    return px.toDouble() / resources.displayMetrics.density
}

fun Context.pxToPt(px: Float): Double {
    return px.toDouble() / resources.displayMetrics.density
}

fun Context.ptToPx(pt: Double): Int {
    return ceil(pt * resources.displayMetrics.density).toInt()
}

fun Context.ptToPxFloat(pt: Double): Float {
    return ceil(pt * resources.displayMetrics.density).toFloat()
}


actual class Kontext(
    val context: Context) {

    actual fun requestAnimationFrame(callback: () -> Unit) {
        Choreographer.getInstance().postFrameCallback {
            callback()
        }
    }


}