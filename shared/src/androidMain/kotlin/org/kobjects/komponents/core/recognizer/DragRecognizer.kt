package org.kobjects.komponents.core.recognizer

import org.kobjects.komponents.core.Widget

actual class DragRecognizer actual constructor(update: (DragRecognizer) -> Unit) : GestureRecognizer() {
    actual val update = update

    var velocityX = 0f
    var velocityY = 0f

    actual fun translation(widget: Widget): Pair<Double, Double> {
       var start = widget.fromRawCoordinates(rawStartX, rawStartY)
        var current = widget.fromRawCoordinates(rawCurrentX, rawCurrentY)
        return Pair(current.first - start.first, current.second - start.second)
    }
}