package org.kobjects.komponents.core

import android.content.Context
import android.content.DialogInterface
import android.view.Choreographer
import androidx.appcompat.app.AlertDialog
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


actual class Context(
    val context: Context
) {

    actual fun requestAnimationFrame(callback: (Double) -> Unit) {
        Choreographer.getInstance().postFrameCallback {
            callback(it / 1000_000_000.0)
        }
    }

    actual fun alert(
        title: String,
        okAction: Action,
        cancelAction: Action?
    ) {
        val alert = AlertDialog.Builder(context)
        alert.setTitle(title)
        if (cancelAction != null) {
            alert.setNegativeButton(cancelAction.title) { alert, index ->
                cancelAction.handler(cancelAction)
            }
        }
        alert.setPositiveButton(okAction.title) { alert, index ->
            okAction.handler(okAction)
        }
        alert.show()
    }

    actual fun getTimestamp(): Double {
       return System.currentTimeMillis() / 1000.0
    }


}