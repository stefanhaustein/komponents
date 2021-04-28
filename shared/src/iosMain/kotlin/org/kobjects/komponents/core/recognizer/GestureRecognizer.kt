package org.kobjects.komponents.core.recognizer

import org.kobjects.komponents.core.Widget
import platform.Foundation.NSLog
import platform.QuartzCore.CACurrentMediaTime
import platform.UIKit.*

actual abstract class GestureRecognizer {
    abstract fun attach(view: Widget)
    actual var state: GestureState = GestureState.END
    actual var timestamp: Double = 0.0

    fun updateCommon(recognizer: UIGestureRecognizer) {
        timestamp = CACurrentMediaTime()
        state = when (recognizer.state) {
            UIGestureRecognizerStateBegan -> GestureState.START
            UIGestureRecognizerStateChanged -> GestureState.UPDATE
            UIGestureRecognizerStateFailed -> GestureState.CANCEL
            UIGestureRecognizerStateEnded -> GestureState.END
            else -> GestureState.UPDATE
        }
    }
}