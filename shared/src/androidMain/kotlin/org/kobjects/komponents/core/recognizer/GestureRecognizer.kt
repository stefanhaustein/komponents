package org.kobjects.komponents.core.recognizer

import org.kobjects.komponents.core.Widget

actual abstract class GestureRecognizer {
    actual var state: GestureState = GestureState.END
    var rawStartX: Float = 0f
    var rawStartY: Float = 0f
    var rawCurrentX: Float = 0f
    var rawCurrentY: Float = 0f


}