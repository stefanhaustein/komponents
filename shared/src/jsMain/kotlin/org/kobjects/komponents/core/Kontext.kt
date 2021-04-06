package org.kobjects.komponents.core

import kotlinx.browser.window
import org.w3c.dom.Document

actual class Kontext(
    val document: Document
) {
    actual fun requestAnimationFrame(callback: () -> Unit) {
        window.requestAnimationFrame {
            callback()
        }
    }


}