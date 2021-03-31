package org.kobjects.komponents.core

import kotlinx.cinterop.useContents
import org.kobjects.komponents.core.mobile.ChildLayout
import org.kobjects.komponents.core.mobile.MeasurementMode
import platform.CoreGraphics.CGRectMake
import platform.CoreGraphics.CGSizeMake
import platform.UIKit.intrinsicContentSize
import platform.UIKit.setFrame
import platform.UIKit.sizeThatFits

class UIChildLayout(override val positioned: GridArea) : ChildLayout {
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
            if (widthMode != MeasurementMode.UNSPECIFIED
                && heightMode != MeasurementMode.UNSPECIFIED
            ) {
                uiView.sizeThatFits(CGSizeMake(width, height)).useContents {
                    measuredWidth = this.width
                    measuredHeight = this.height
                }
            } else {
                uiView.intrinsicContentSize.useContents {
                    measuredWidth = this.width
                    measuredHeight = this.height
                }
            }
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