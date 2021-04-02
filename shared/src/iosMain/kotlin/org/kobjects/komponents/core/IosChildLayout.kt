package org.kobjects.komponents.core

import kotlinx.cinterop.useContents
import org.kobjects.komponents.core.mobile.ChildLayout
import org.kobjects.komponents.core.mobile.MeasurementMode
import platform.CoreGraphics.CGFLOAT_MAX
import platform.CoreGraphics.CGRectMake
import platform.CoreGraphics.CGSizeMake
import platform.UIKit.*

class IosChildLayout(override val positioned: GridArea) : ChildLayout {
    override var column: Int = 0
    override var row: Int = 0
    var measuredWidth: Double = 0.0
    var measuredHeight: Double = 0.0

    override fun measure(
        widthMode: MeasurementMode,
        width: Double,
        heightMode: MeasurementMode,
        height: Double
    ) {
        val uiView = positioned.view.getView()
        if (widthMode != MeasurementMode.EXACTLY
            || heightMode != MeasurementMode.EXACTLY) {

            val size = CGSizeMake(
                if (widthMode == MeasurementMode.UNSPECIFIED) CGFLOAT_MAX else width,
                if (heightMode == MeasurementMode.UNSPECIFIED) CGFLOAT_MAX else height)

            uiView.sizeThatFits(size).useContents {
                    measuredWidth = this.width
                    measuredHeight = this.height
                }

            /*
                uiView.intrinsicContentSize().useContents {
                    measuredWidth = if (this.width == UIViewNoIntrinsicMetric) 44.0 else this.width
                    measuredHeight = if (this.height == UIViewNoIntrinsicMetric) 44.0 else this.height
                }

            if (uiView is UITextView) {
                uiView.text ="$measuredWidth x $measuredHeight"
            }*/
        }
        if (widthMode == MeasurementMode.EXACTLY) {
            measuredWidth = width;
        }
        if (heightMode == MeasurementMode.EXACTLY) {
            measuredHeight = height;
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