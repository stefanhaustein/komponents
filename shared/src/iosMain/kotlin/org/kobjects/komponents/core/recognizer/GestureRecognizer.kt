package org.kobjects.komponents.core.recognizer

import org.kobjects.komponents.core.KView

actual abstract class GestureRecognizer {
    abstract fun attach(view: KView)
}