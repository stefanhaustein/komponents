package org.kobjects.komponents.core.recognizer

import org.kobjects.komponents.core.Widget

actual class DragRecognizer actual constructor(update: (DragRecognizer) -> Unit) : GestureRecognizer() {
    actual val update = update

    actual fun translation(widget: Widget): Pair<Double, Double> {
       var start = startPosition(widget)
        var current = currentPosition(widget)
        return Pair(current.first - start.first, current.second - start.second)
    }

}