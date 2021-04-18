package org.kobjects.komponents.core

import org.kobjects.komponents.core.recognizer.GestureRecognizer

expect abstract class KView() {

    val transformation: Transformation

    val clientX: Double
    val clientY: Double
    val clientWidth: Double
    val clientHeight: Double

    fun setBackgroundColor(color: UInt)

    fun addGestureRecognizer(gestureRecognizer: GestureRecognizer)
}