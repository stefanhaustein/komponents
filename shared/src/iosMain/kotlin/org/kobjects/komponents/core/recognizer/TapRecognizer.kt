package org.kobjects.komponents.core.recognizer

import kotlinx.cinterop.ObjCAction
import org.kobjects.komponents.core.Widget
import platform.Foundation.NSLog
import platform.UIKit.*

actual class TapRecognizer actual constructor(recognized: (TapRecognizer) -> Unit) : GestureRecognizer() {
    actual val recognized = recognized

    lateinit var view: Widget
    lateinit var tapGestureRecognizer: UITapGestureRecognizer

    @ObjCAction
    fun handleTapGesture() {
        if (tapGestureRecognizer.state == UIGestureRecognizerStateEnded) {
            recognized(this)
        }
    }


    override fun attach(view: Widget) {
        this.view = view
        this.tapGestureRecognizer = UITapGestureRecognizer(
            target = this,
            action = platform.objc.sel_registerName("handleTapGesture"))
        view.getView().addGestureRecognizer(tapGestureRecognizer)
    }

}