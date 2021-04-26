package org.kobjects.komponents.core

import kotlinx.browser.window
import org.w3c.dom.Document

actual class Context(
    val document: Document
) {
    actual fun requestAnimationFrame(callback: (Double) -> Unit) {
        window.requestAnimationFrame {
            callback(it)
        }
    }

    actual fun alert(
        title: String,
        okAction: Action,
        cancelAction: Action?
    ) {
        if (cancelAction == null) {
            window.alert(title)
            okAction.handler(okAction)
        } else if (window.confirm(title)) {
            okAction.handler(okAction)
        } else {
            cancelAction.handler(cancelAction)
        }
    }


}