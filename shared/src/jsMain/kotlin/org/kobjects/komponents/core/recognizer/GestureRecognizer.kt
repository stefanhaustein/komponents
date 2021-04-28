package org.kobjects.komponents.core.recognizer

import org.kobjects.komponents.core.Widget
import org.w3c.dom.events.Event

actual abstract class GestureRecognizer {
    abstract fun attach(view: Widget)
    actual var state: GestureState = GestureState.END
    actual var timestamp: Double = 0.0

    fun updateCommon(event: Event, newState: GestureState) {
        timestamp = event.timeStamp.toDouble() / 1000.0
        state = newState
    }
}