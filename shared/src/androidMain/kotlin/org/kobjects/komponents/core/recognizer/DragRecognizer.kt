package org.kobjects.komponents.core.recognizer

actual class DragRecognizer actual constructor(update: (DragRecognizer) -> Unit) : GestureRecognizer() {
    actual val update = update
    actual var state = DragState.END
    actual var distanceX = 0.0
    actual var distanceY = 0.0
}