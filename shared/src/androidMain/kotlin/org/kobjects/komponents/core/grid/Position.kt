package org.kobjects.komponents.core.grid

import org.kobjects.komponents.core.KView
import org.kobjects.komponents.core.grid.mobile.MeasurementMode
import org.kobjects.komponents.core.grid.mobile.ResolvedPosition
import org.kobjects.komponents.core.pxToPt

actual open class Position actual constructor(
    actual val gridLayout: KGridLayout,
    actual val view: KView
) : ResolvedPosition {
    override var resolvedColumn = 0
    override var resolvedRow = 0
    var x = 0.0
    var y = 0.0

    actual fun notifyChanged() {
        gridLayout.notifyPositionChanged()
    }

    override fun layout(
        widthMode: MeasurementMode,
        width: Double,
        heightMode: MeasurementMode,
        height: Double,
        measureOnly: Boolean
    ) {
        val childView = view.getView()
        childView.measure(
            gridLayout.layout.modeAndSizeToAndroid(widthMode, width),
            gridLayout.layout.modeAndSizeToAndroid(heightMode, height))
    }

    override fun measuredHeight(): Double {
        return gridLayout.layout.context.pxToPt(view.getView().measuredHeight)
    }

    override fun measuredWidth(): Double {
        return gridLayout.layout.context.pxToPt(view.getView().measuredWidth)
    }

    override fun setPosition(x: Double, y: Double) {
        this.x = x
        this.y = y
    }
}