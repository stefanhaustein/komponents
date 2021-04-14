package org.kobjects.komponents.core.grid.mobile

import org.kobjects.komponents.core.grid.Cell

interface ResolvedPosition {
    var resolvedColumn: Int
    var resolvedRow: Int

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