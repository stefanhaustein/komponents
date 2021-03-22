package org.kobjects.komponents.core

import android.content.Context
import android.view.ViewGroup
import org.kobjects.komponents.core.mobile.ChildLayout
import org.kobjects.komponents.core.mobile.MeasurementMode
import org.kobjects.komponents.core.mobile.applyGridLayout
import kotlin.math.ceil


class GridLayout(context: Context, val container: KGrid) : ViewGroup(context) {

    var columnGap = 0.0
    var rowGap = 0.0


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
                pxToPt(MeasureSpec.getSize(heightMeasureSpec)));

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

    inner class LayoutParams(override val positioned: Positioned) : ViewGroup.LayoutParams(0, 0), ChildLayout {
        var x = 0.0
        var y = 0.0

        override fun measure(
            widthMode: MeasurementMode,
            width: Double,
            heightMode: MeasurementMode,
            height: Double
        ): Pair<Double, Double> {
            val childView = positioned.component.getView()
            childView.measure(modeAndSizeToAndroid(widthMode, width), modeAndSizeToAndroid(heightMode, height))
            return Pair(pxToPt(childView.measuredWidth), pxToPt(childView.measuredHeight));
        }

        override fun setPosition(x: Double, y: Double) {
            this.x = x
            this.y = y
        }
    }

}