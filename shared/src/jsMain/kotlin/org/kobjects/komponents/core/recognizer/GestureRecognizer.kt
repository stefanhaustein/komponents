package org.kobjects.komponents.core.recognizer

import org.kobjects.komponents.core.Widget

actual abstract class GestureRecognizer {
    abstract fun attach(view: Widget)
    actual var state: GestureState = GestureState.END
}