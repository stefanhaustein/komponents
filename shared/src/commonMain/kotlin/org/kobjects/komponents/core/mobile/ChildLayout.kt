package org.kobjects.komponents.core.mobile

import org.kobjects.komponents.core.Align
import org.kobjects.komponents.core.GridArea

interface ChildLayout {
    val positioned: GridArea
    var column: Int
    var row: Int


    fun measure(widthMode: MeasurementMode, width: Double, heightMode: MeasurementMode, height: Double)

    fun measuredWidth(): Double
    fun measuredHeight(): Double

    fun setPosition(x: Double, y: Double)
}