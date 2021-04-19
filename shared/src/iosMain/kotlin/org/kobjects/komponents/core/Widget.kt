package org.kobjects.komponents.core

import kotlinx.cinterop.useContents
import org.kobjects.komponents.core.grid.mobile.MeasurementMode
import org.kobjects.komponents.core.recognizer.GestureRecognizer
import platform.CoreGraphics.*
import platform.Foundation.NSLog
import platform.UIKit.*
import kotlin.math.PI

abstract actual class Widget {
    abstract fun getView(): UIView

    actual val transformation: Transformation by lazy {
        TransformationImpl()
    }

    actual val clientX: Double
        get() = getView().center.useContents {
            this.x - clientWidth / 2
        }
    actual val clientY: Double
        get() = getView().center.useContents {
            this.y - clientHeight / 2
        }

    actual val clientWidth: Double
        get() = getView().bounds.useContents {
            this.size.width
        }
    actual val clientHeight: Double
        get() = getView().bounds.useContents {
            this.size.height
        }

    var recognizers = mutableListOf<GestureRecognizer>()

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

        val msg = "Measuring $widthMode:$availableWidth $heightMode:$availableHeight measureOnly:$measureOnly for $this"

        NSLog( msg);

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

  //      NSLog("Measured size %f %f %@", measuredWidth, measuredHeight, this.getView())

        return Pair(measuredWidth, measuredHeight)
    }


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
            val transform = CGAffineTransformMakeTranslation(x, y)
            if (rotation != 0.0) {
                CGAffineTransformRotate(transform, rotation * PI / 180)
            }
            getView().transform = transform
        }
    }

    actual fun addGestureRecognizer(gestureRecognizer: GestureRecognizer) {
        recognizers.add(gestureRecognizer)
        gestureRecognizer.attach(this)
    }

}