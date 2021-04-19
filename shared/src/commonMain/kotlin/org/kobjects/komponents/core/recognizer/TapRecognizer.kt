package org.kobjects.komponents.core.recognizer

expect class TapRecognizer(recognized: (TapRecognizer) -> Unit) : GestureRecognizer {
    val recognized: (TapRecognizer) -> Unit
}