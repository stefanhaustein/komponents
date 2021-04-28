package org.kobjects.komponents.core.recognizer

import kotlinx.cinterop.ObjCAction
import kotlinx.cinterop.useContents
import org.kobjects.komponents.core.Widget
import platform.Foundation.NSLog
import platform.QuartzCore.CACurrentMediaTime
import platform.UIKit.*

actual class DragRecognizer actual constructor(update: (DragRecognizer) -> Unit) : GestureRecognizer() {
    actual val update = update

    lateinit var view: Widget
    lateinit var panGestureRecognizer: UIPanGestureRecognizer

    @ObjCAction
    fun handlePanGesture() {
        updateCommon(panGestureRecognizer)
        update(this)
    }


    override fun attach(view: Widget) {
        this.view = view
        this.panGestureRecognizer = UIPanGestureRecognizer(
            target = this,
            action = platform.objc.sel_registerName("handlePanGesture"))
        view.getView().addGestureRecognizer(panGestureRecognizer)
    }

    actual fun translation(widget: Widget): Pair<Double, Double> {
        return panGestureRecognizer.translationInView(widget.getView()).useContents {
            Pair(x, y)
        }
    }

}