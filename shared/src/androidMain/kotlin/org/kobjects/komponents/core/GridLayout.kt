package org.kobjects.komponents.core

import android.content.Context
import android.view.ViewGroup
import org.kobjects.komponents.core.mobile.ChildLayout
import org.kobjects.komponents.core.mobile.MeasurementMode
import org.kobjects.komponents.core.mobile.applyGridLayout
import kotlin.math.ceil


class GridLayout(context: Context, val container: KGridLayout) : ViewGroup(context) {

    fun pxToPt(px: Int): Double {
        return px.toDouble() / context.resources.displayMetrics.density
    }

    fun ptToPx(pt: Double): Int {
        return ceil(pt * context.resources.displayMetrics.density).toInt()
    }

    fun modeAndSizeToAndroid(mode: MeasurementMode, size: Double): Int {
        return ptToPx(size) or
            when(mode) {
             MeasurementMode.UNSPECIFIED -> MeasureSpec.UNSPECIFIED
             MeasurementMode.AT_MOST -> MeasureSpec.AT_MOST
             MeasurementMode.EXACTLY -> MeasureSpec.EXACTLY
            }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

            val result = applyGridLayout(
                container,
                container.children,
                MeasurementMode.EXACTLY,
                pxToPt(MeasureSpec.getSize(widthMeasureSpec)),
                MeasurementMode.EXACTLY,
                pxToPt(MeasureSpec.getSize(heightMeasureSpec)),
            /* measureOnly= */ false);

            setMeasuredDimension(ptToPx(result.first), ptToPx(result.second));
    }


    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {

        for (i in 0 until childCount) {
            val child = getChildAt(i)
            val layoutParams = child.layoutParams as LayoutParams

            val xPos = ptToPx(layoutParams.x)
            val yPos = ptToPx(layoutParams.y)

            child.layout(xPos, yPos, xPos + child.measuredWidth, yPos + child.measuredHeight)
        }
    }

    inner class LayoutParams(override val positioned: GridArea) : ViewGroup.LayoutParams(0, 0), ChildLayout {
        var x = 0.0
        var y = 0.0
        override var column = 0
        override var row = 0

        override fun layout(
            widthMode: MeasurementMode,
            width: Double,
            heightMode: MeasurementMode,
            height: Double,
            measureOnly: Boolean
        ) {
            val childView = positioned.view.getView()
            childView.measure(modeAndSizeToAndroid(widthMode, width), modeAndSizeToAndroid(heightMode, height))
        }

        override fun measuredHeight(): Double {
            return pxToPt(positioned.view.getView().measuredHeight)
        }

        override fun measuredWidth(): Double {
            return pxToPt(positioned.view.getView().measuredWidth)
        }

        override fun setPosition(x: Double, y: Double) {
            this.x = x
            this.y = y
        }
    }

}