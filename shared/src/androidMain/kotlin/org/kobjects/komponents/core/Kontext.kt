package org.kobjects.komponents.core

import android.content.Context
import android.view.Choreographer

actual class Kontext(
    val context: Context) {

    actual fun requestAnimationFrame(callback: () -> Unit) {
        Choreographer.getInstance().postFrameCallback {
            callback()
        }
    }

}