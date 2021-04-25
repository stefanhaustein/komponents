package org.kobjects.komponents.core.recognizer

import org.kobjects.komponents.core.Widget

expect abstract class GestureRecognizer() {
    var state: GestureState
}