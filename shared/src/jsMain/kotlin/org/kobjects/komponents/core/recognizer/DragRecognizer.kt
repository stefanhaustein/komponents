package org.kobjects.komponents.core.recognizer

import org.kobjects.komponents.core.Widget
import org.w3c.dom.pointerevents.PointerEvent

actual class DragRecognizer actual constructor(update: (DragRecognizer) -> Unit) : GestureRecognizer() {
    actual val update = update
    actual var state = DragState.END
    actual var distanceX = 0.0
    actual var distanceY = 0.0

    var startX = 0
    var startY = 0

    lateinit var view: Widget

    override fun attach(view: Widget) {
        this.view = view

        val element = view.getElement()

        element.addEventListener("pointerdown", {
            console.log("pointerdown")
            val pointerEvent = it as PointerEvent
            if (pointerEvent.isPrimary) {
                state = DragState.START
                distanceX = 0.0
                distanceY = 0.0
                element.setPointerCapture(pointerEvent.pointerId)
                startX = pointerEvent.screenX
                startY = pointerEvent.screenY
                update(this@DragRecognizer)
            }
        });
        element.addEventListener("pointerup", {
            console.log("pointerup", it)
            val pointerEvent = it as PointerEvent
            if (pointerEvent.isPrimary) {
                state = DragState.END
                element.releasePointerCapture(pointerEvent.pointerId)
                update(this)
            }
        });
        element.addEventListener("pointercanel", {
            console.log("pointermove", it)
            val pointerEvent = it as PointerEvent
            if (pointerEvent.isPrimary) {
                state = DragState.END
                update(this)
            }
        });
        element.addEventListener("pointermove", {
            console.log("pointermove", it)
            val pointerEvent = it as PointerEvent
            if (pointerEvent.isPrimary && (state == DragState.START || state == DragState.UPDATE)) {
                state = DragState.UPDATE
                distanceX = (pointerEvent.screenX - startX).toDouble()
                distanceY = (pointerEvent.screenY - startY).toDouble()
                update(this)
            }
        });
    }
}