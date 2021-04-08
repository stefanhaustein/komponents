package org.kobjects.komponents.core.recognizer


enum class DragState {
    START, UPDATE, END
}

expect class DragRecognizer(update: (DragRecognizer) -> Unit) : GestureRecognizer {
    val update: (DragRecognizer) -> Unit
    var state: DragState
    var distanceX: Double
    var distanceY: Double

}