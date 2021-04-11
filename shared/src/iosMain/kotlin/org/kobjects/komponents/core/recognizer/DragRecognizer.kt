package org.kobjects.komponents.core.recognizer

import kotlinx.cinterop.ObjCAction
import kotlinx.cinterop.useContents
import org.kobjects.komponents.core.KView
import platform.Foundation.NSLog
import platform.UIKit.*

actual class DragRecognizer actual constructor(update: (DragRecognizer) -> Unit) : GestureRecognizer() {
    actual val update = update
    actual var state = DragState.END
    actual var distanceX = 0.0
    actual var distanceY = 0.0

    lateinit var view: KView
    lateinit var panGestureRecognizer: UIPanGestureRecognizer

    @ObjCAction
    fun handlePanGesture() {
        NSLog("handlePanGesture: %@", panGestureRecognizer)
        state = when (panGestureRecognizer.state) {
            UIGestureRecognizerStateBegan -> DragState.START
            UIGestureRecognizerStateChanged -> DragState.UPDATE
            UIGestureRecognizerStateFailed,
            UIGestureRecognizerStateEnded -> DragState.END
            else -> DragState.UPDATE
        }
        panGestureRecognizer.translationInView(view.getView()).useContents {
            distanceX = x
            distanceY = y
            NSLog("translationInView: %f %f", x, y)
        }
        update(this)
    }


    override fun attach(view: KView) {
        this.view = view
        this.panGestureRecognizer = UIPanGestureRecognizer(
            target = this,
            action = platform.objc.sel_registerName("handlePanGesture"))
        view.getView().addGestureRecognizer(panGestureRecognizer)
    }

}