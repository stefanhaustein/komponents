package org.kobjects.komponents.core

import org.kobjects.komponents.core.recognizer.GestureRecognizer

expect abstract class KView {

    val transformation: Transformation

    fun setBackgroundColor(color: UInt)

    fun addGestureRecognizer(gestureRecognizer: GestureRecognizer)
}