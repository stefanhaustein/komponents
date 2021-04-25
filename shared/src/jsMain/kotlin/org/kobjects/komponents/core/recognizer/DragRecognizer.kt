package org.kobjects.komponents.core.recognizer

import org.kobjects.komponents.core.Widget
import org.w3c.dom.pointerevents.PointerEvent

actual class DragRecognizer actual constructor(update: (DragRecognizer) -> Unit) : GestureRecognizer() {
    actual val update = update

    var startX = 0.0
    var startY = 0.0

    var currentX = 0.0
    var currentY = 0.0

    lateinit var view: Widget

    override fun attach(view: Widget) {
        this.view = view

        val element = view.getElement()

        element.addEventListener("pointerdown", {
            console.log("pointerdown")
            val pointerEvent = it as PointerEvent
            if (pointerEvent.isPrimary) {
                state = GestureState.START
                element.setPointerCapture(pointerEvent.pointerId)
                startX = pointerEvent.clientX.toDouble()
                startY = pointerEvent.clientY.toDouble()
                currentX = startX
                currentY = startY
                update(this@DragRecognizer)
            }
        });
        element.addEventListener("pointerup", {
            console.log("pointerup", it)
            val pointerEvent = it as PointerEvent
            if (pointerEvent.isPrimary) {
                state = GestureState.END
                element.releasePointerCapture(pointerEvent.pointerId)
                update(this)
            }
        });
        element.addEventListener("pointercanel", {
            console.log("pointermove", it)
            val pointerEvent = it as PointerEvent
            if (pointerEvent.isPrimary) {
                state = GestureState.END
                update(this)
            }
        });
        element.addEventListener("pointermove", {
            console.log("pointermove", it)
            val pointerEvent = it as PointerEvent
            if (pointerEvent.isPrimary && (state == GestureState.START || state == GestureState.UPDATE)) {
                state = GestureState.UPDATE
                currentX = pointerEvent.clientX.toDouble()
                currentY = pointerEvent.clientY.toDouble()
                update(this)
            }
        });
    }

    actual fun translation(widget: Widget): Pair<Double, Double> {
            var start = widget.fromClientCoordinates(startX, startY)
            var current = widget.fromClientCoordinates(currentX, currentY)
            return Pair(current.first - start.first, current.second - start.second)
        }
}