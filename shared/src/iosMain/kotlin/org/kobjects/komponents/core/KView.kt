package org.kobjects.komponents.core

import kotlinx.cinterop.useContents
import org.kobjects.komponents.core.mobile.MeasurementMode
import platform.CoreGraphics.CGAffineTransform
import platform.CoreGraphics.CGAffineTransformMakeRotation
import platform.CoreGraphics.CGFLOAT_MAX
import platform.CoreGraphics.CGSizeMake
import platform.UIKit.UIColor
import platform.UIKit.UIView
import platform.UIKit.backgroundColor
import platform.UIKit.sizeThatFits
import kotlin.math.PI

abstract actual class KView {
    abstract fun getView(): UIView


    actual fun setBackgroundColor(color: UInt) {
        getView().backgroundColor = UIColor(
            red = ((color shr 16) and 255u).toDouble() / 255.0,
            green = ((color shr 8) and 255u).toDouble() / 255.0,
            blue = (color and 255u).toDouble() / 255.0,
            alpha = ((color shr 24) and 255u).toDouble() / 255.0
        )
    }

    open fun layout(
        availableWidth: Double,
        widthMode: MeasurementMode,
        availableHeight: Double,
        heightMode: MeasurementMode,
        measureOnly: Boolean
    ): Pair<Double, Double> {

        var measuredHeight = 0.0
        var measuredWidth = 0.0
        if (widthMode != MeasurementMode.EXACTLY ||
            heightMode != MeasurementMode.EXACTLY
        ) {
            getView().sizeThatFits(
                CGSizeMake(
                    if (widthMode == MeasurementMode.UNSPECIFIED) CGFLOAT_MAX else availableWidth,
                    if (heightMode == MeasurementMode.UNSPECIFIED) CGFLOAT_MAX else availableHeight
                )
            ).useContents {
                measuredWidth = this.width
                measuredHeight = this.height
            }
        }
        if (widthMode == MeasurementMode.EXACTLY) {
            measuredWidth = availableWidth
        }
        if (heightMode == MeasurementMode.EXACTLY) {
            measuredHeight = availableHeight
        }
        return Pair(measuredWidth, measuredHeight)
    }

    actual val transformation: Transformation by lazy {
        TransformationImpl()
    }

    // TODO: Implement
    inner class TransformationImpl : Transformation {
        override var rotation: Double = 0.0
            set(value) {
                field = value
                update()
            }
        override var x: Double = 0.0
            set(value) {
                field = value
                update()
            }
        override var y: Double = 0.0
            set(value) {
                field = value
                update()
            }

        fun update() {
            getView().transform = CGAffineTransformMakeRotation(rotation * PI / 180.0)
        }
    }
}