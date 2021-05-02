package org.kobjects.komponents.core

import org.kobjects.komponents.core.grid.GridLayout
import org.kobjects.komponents.core.recognizer.GestureRecognizer

fun Widget.toParentCoordinates(x: Double, y: Double): Pair<Double, Double> {
    val transformed = this.transformation.transform(x - offsetWidth / 2, y - offsetHeight / 2)
    return Pair(
        transformed.first + offsetWidth / 2 + offsetLeft,
        transformed.second + offsetHeight / 2 + offsetTop)
}

fun Widget.fromParentCoordinates(x: Double, y: Double): Pair<Double, Double> {
    val transformed = this.transformation.unTransform(x - offsetLeft - offsetWidth / 2, y - offsetTop - offsetHeight / 2)
    return Pair(
        transformed.first + offsetWidth / 2,
        transformed.second + offsetHeight / 2)
}

/**
 * Translates the given coordinates
 */
fun Widget.translateTo(widget: Widget, x: Double, y: Double): Pair<Double, Double> {
    var source = this
    var current = Pair(x, y)
    if (widget == this) {
        return current
    }
    while (true) {
        val parent = source.getParent() ?: break
        current = source.toParentCoordinates(current.first, current.second)
        source = parent
    }
    val parent = widget.getParent()
    if (parent != null) {
        current = source.translateTo(parent, current.first, current.second)
    }
    return widget.fromParentCoordinates(current.first, current.second)
}


expect abstract class Widget() {
    val transformation: Transformation
    val offsetLeft: Double
    val offsetTop: Double
    val offsetWidth: Double
    val offsetHeight: Double
    var hidden: Boolean
    var opacity: Double
    var zIndex: Int

    fun setBackgroundColor(color: UInt)

    fun addGestureRecognizer(gestureRecognizer: GestureRecognizer)

    fun getParent(): GridLayout?

}