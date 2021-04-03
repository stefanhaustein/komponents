package org.kobjects.komponents.core.mobile

import org.kobjects.komponents.core.Align
import org.kobjects.komponents.core.GridArea
import org.kobjects.komponents.core.Size

interface ChildLayout {
    val positioned: GridArea
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