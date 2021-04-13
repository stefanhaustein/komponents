package org.kobjects.komponents.core.grid

import org.kobjects.komponents.core.grid.mobile.ResolvedPosition
import org.kobjects.komponents.core.grid.mobile.MeasurementMode
import platform.CoreGraphics.CGPointMake
import platform.CoreGraphics.CGRectMake
import platform.UIKit.*

class IosResolvedPosition(override val positioned: Cell) : ResolvedPosition {
    override var resolvedColumn: Int = 0
    override var resolvedRow: Int = 0
    var measuredWidth: Double = 0.0
    var measuredHeight: Double = 0.0

    override fun layout(
        widthMode: MeasurementMode,
        width: Double,
        heightMode: MeasurementMode,
        height: Double,
        measureOnly: Boolean
    ) {
        positioned.view.layout(width, widthMode, height, heightMode, measureOnly).also {
            measuredWidth = it.first
            measuredHeight = it.second
            if (!measureOnly) {
                positioned.view.getView().setBounds(CGRectMake(0.0, 0.0, it.first, it.second))
            }
        }
    }

    override fun measuredWidth(): Double {
        return measuredWidth
    }

    override fun measuredHeight(): Double {
        return measuredHeight
    }

    override fun setPosition(x: Double, y: Double) {
        val uiView = positioned.view.getView()
        uiView.setCenter(CGPointMake(x + measuredWidth / 2, y + measuredHeight / 2))
    }
}