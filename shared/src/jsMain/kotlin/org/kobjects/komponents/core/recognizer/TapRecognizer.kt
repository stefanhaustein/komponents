package org.kobjects.komponents.core.recognizer

import org.kobjects.komponents.core.Widget
import org.w3c.dom.pointerevents.PointerEvent

actual class TapRecognizer actual constructor(recognized: (TapRecognizer) -> Unit) : GestureRecognizer() {
    actual val recognized = recognized

    lateinit var view: Widget

    override fun attach(view: Widget) {
        this.view = view

        val element = view.getElement()

        element.addEventListener("pointerup", {
            val pointerEvent = it as PointerEvent
            if (pointerEvent.isPrimary) {
                updateCommon(it, GestureState.END)
                recognized(this)
            }
        })
    }
}