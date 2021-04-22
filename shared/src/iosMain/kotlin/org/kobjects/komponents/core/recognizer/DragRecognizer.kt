package org.kobjects.komponents.core.recognizer

import kotlinx.cinterop.ObjCAction
import kotlinx.cinterop.useContents
import org.kobjects.komponents.core.Widget
import platform.Foundation.NSLog
import platform.UIKit.*

actual class DragRecognizer actual constructor(update: (DragRecognizer) -> Unit) : GestureRecognizer() {
    actual val update = update
    actual var state = DragState.END
    actual var distanceX = 0.0
    actual var distanceY = 0.0

    lateinit var view: Widget
    lateinit var panGestureRecognizer: UIPanGestureRecognizer

    @ObjCAction
    fun handlePanGesture() {
        NSLog("handlePanGesture: %@", panGestureRecognizer)
        state = when (panGestureRecognizer.state) {
            UIGestureRecognizerStateBegan -> DragState.START
            UIGestureRecognizerStateChanged -> DragState.UPDATE
            UIGestureRecognizerStateFailed -> DragState.CANCEL
            UIGestureRecognizerStateEnded -> DragState.END
            else -> DragState.UPDATE
        }
        panGestureRecognizer.translationInView(view.getView().superview).useContents {
            distanceX = x
            distanceY = y
            NSLog("translationInView: %f %f", x, y)
        }
        update(this)
    }


    override fun attach(view: Widget) {
        this.view = view
        this.panGestureRecognizer = UIPanGestureRecognizer(
            target = this,
            action = platform.objc.sel_registerName("handlePanGesture"))
        view.getView().addGestureRecognizer(panGestureRecognizer)
    }

}