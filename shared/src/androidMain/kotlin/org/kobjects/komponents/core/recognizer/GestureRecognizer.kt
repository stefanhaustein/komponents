package org.kobjects.komponents.core.recognizer

import org.kobjects.komponents.core.Widget

actual abstract class GestureRecognizer {
    actual var state: GestureState = GestureState.END
    actual var timestamp: Double = 0.0
    var rawStartX: Float = 0f
    var rawStartY: Float = 0f
    var rawCurrentX: Float = 0f
    var rawCurrentY: Float = 0f


}