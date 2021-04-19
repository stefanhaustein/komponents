package org.kobjects.komponents.core.recognizer


enum class DragState {
    START, UPDATE, END, CANCEL
}

expect class DragRecognizer(update: (DragRecognizer) -> Unit) : GestureRecognizer {
    val update: (DragRecognizer) -> Unit
    var state: DragState
    var distanceX: Double
    var distanceY: Double

}