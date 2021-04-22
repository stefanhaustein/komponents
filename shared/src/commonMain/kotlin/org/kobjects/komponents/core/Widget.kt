package org.kobjects.komponents.core

import org.kobjects.komponents.core.recognizer.GestureRecognizer

expect abstract class Widget() {

    val transformation: Transformation
    val offsetLeft: Double
    val offsetTop: Double
    val offsetWidth: Double
    val offsetHeight: Double

    var opacity: Double

    fun setBackgroundColor(color: UInt)

    fun addGestureRecognizer(gestureRecognizer: GestureRecognizer)
}