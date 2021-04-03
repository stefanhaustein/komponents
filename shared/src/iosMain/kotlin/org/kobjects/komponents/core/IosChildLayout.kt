package org.kobjects.komponents.core

import org.kobjects.komponents.core.mobile.ChildLayout
import org.kobjects.komponents.core.mobile.MeasurementMode
import platform.CoreGraphics.CGRectMake
import platform.UIKit.*

class IosChildLayout(override val positioned: GridArea) : ChildLayout {
    override var column: Int = 0
    override var row: Int = 0
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
        }
    }

    override fun measuredWidth(): Double {
        return measuredWidth
    }

    override fun measuredHeight(): Double {
        return measuredHeight
    }

    override fun setPosition(x: Double, y: Double) {
        positioned.view.getView().setFrame(CGRectMake(x, y, measuredWidth, measuredHeight))
    }
}