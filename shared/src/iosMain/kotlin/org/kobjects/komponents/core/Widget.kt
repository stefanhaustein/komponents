package org.kobjects.komponents.core

import kotlinx.cinterop.useContents
import org.kobjects.komponents.core.grid.GridLayout
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

    actual val offsetLeft: Double
        get() = getView().center.useContents {
            this.x - offsetWidth / 2
        }
    actual val offsetTop: Double
        get() = getView().center.useContents {
            this.y - offsetHeight / 2
        }

    actual val offsetWidth: Double
        get() = getView().bounds.useContents {
            this.size.width
        }
    actual val offsetHeight: Double
        get() = getView().bounds.useContents {
            this.size.height
        }

    actual var opacity: Double
        get() = getView().alpha
        set(value) {
            getView().alpha = value
        }

    actual var hidden: Boolean
        get() = getView().hidden
        set(value) {
            getView().hidden = value
        }

    actual var zIndex: Int
        get() = getView().layer.zPosition.toInt()
        set(value) {
            getView().layer.zPosition = value.toDouble()
        }

    var recognizers = mutableListOf<GestureRecognizer>()
    var parentImpl: GridLayout? = null

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
        override var scaleX: Double = 1.0
            set(value) {
                field = value
                update()
            }
        override var scaleY: Double = 1.0
            set(value) {
                field = value
                update()
            }
        override var scale: Double?
            get() = if (scaleX == scaleY) scaleX else null
            set(value) {
                scaleX = value ?: 1.0
                scaleY = value ?: 1.0
            }


        override fun transform(x: Double, y: Double): Pair<Double, Double> {
            CGPointApplyAffineTransform(CGPointMake(x, y), getView().transform).useContents {
                return Pair(this.x, this.y)
            }
        }

        override fun unTransform(x: Double, y: Double): Pair<Double, Double> {
            TODO("Not yet implemented")
        }

        fun update() {
            var transform = CGAffineTransformMakeTranslation(x, y)
            if (rotation != 0.0) {
               transform = CGAffineTransformRotate(transform, rotation * PI / 180)
            }
            if (scaleX != 1.0 || scaleY != 1.0) {
                transform = CGAffineTransformScale(transform, scaleX, scaleY)
            }
            getView().transform = transform
        }
    }

    actual fun addGestureRecognizer(gestureRecognizer: GestureRecognizer) {
        recognizers.add(gestureRecognizer)
        gestureRecognizer.attach(this)
        getView().userInteractionEnabled = true
    }

    actual fun getParent(): GridLayout? {
        return parentImpl
    }

}