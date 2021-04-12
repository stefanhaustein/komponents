package org.kobjects.komponents.core.mobile

import org.kobjects.komponents.core.Cell

interface ChildLayout {
    val positioned: Cell
    var column: Int
    var row: Int


    fun layout(
        widthMode: MeasurementMode,
        width: Double,
        heightMode: MeasurementMode,
        height: Double,
        measureOnly: Boolean)

    fun measuredWidth(): Double
    fun measuredHeight(): Double

    fun setPosition(x: Double, y: Double)


}