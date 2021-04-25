package org.kobjects.komponents.core.recognizer

import org.kobjects.komponents.core.Widget


expect class DragRecognizer(update: (DragRecognizer) -> Unit) : GestureRecognizer {
    val update: (DragRecognizer) -> Unit

    fun translation(widget: Widget): Pair<Double, Double>
}