package org.kobjects.komponents.core.recognizer

actual class TapRecognizer actual constructor(
    recognized: (TapRecognizer) -> Unit
) : GestureRecognizer() {
    actual val recognized = recognized
}