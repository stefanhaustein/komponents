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
            val pointerEvent = it as PointerEvent
            if (pointerEvent.isPrimary) {
                updateCommon(it, GestureState.START)
                element.setPointerCapture(pointerEvent.pointerId)
                startX = pointerEvent.clientX.toDouble()
                startY = pointerEvent.clientY.toDouble()
                currentX = startX
                currentY = startY
                update(this@DragRecognizer)
            }
        });
        element.addEventListener("pointerup", {
            val pointerEvent = it as PointerEvent
            if (pointerEvent.isPrimary) {
                updateCommon(it, GestureState.END)
                element.releasePointerCapture(pointerEvent.pointerId)
                update(this)
            }
        });
        element.addEventListener("pointercanel", {
            val pointerEvent = it as PointerEvent
            if (pointerEvent.isPrimary) {
                updateCommon(it, GestureState.END)
                update(this)
            }
        });
        element.addEventListener("pointermove", {
            val pointerEvent = it as PointerEvent
            if (pointerEvent.isPrimary && (state == GestureState.START || state == GestureState.UPDATE)) {
                updateCommon(it, GestureState.UPDATE)
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