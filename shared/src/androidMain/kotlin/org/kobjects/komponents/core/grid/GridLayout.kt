package org.kobjects.komponents.core.grid

import android.content.Context
import android.view.ViewGroup
import org.kobjects.komponents.core.grid.mobile.ResolvedPosition
import org.kobjects.komponents.core.grid.mobile.MeasurementMode
import org.kobjects.komponents.core.grid.mobile.applyGridLayout
import org.kobjects.komponents.core.ptToPx
import org.kobjects.komponents.core.pxToPt


class GridLayout(context: Context, val container: KGridLayout) : ViewGroup(context) {


    fun modeAndSizeToAndroid(mode: MeasurementMode, size: Double): Int {
        return context.ptToPx(size) or
            when(mode) {
             MeasurementMode.UNSPECIFIED -> MeasureSpec.UNSPECIFIED
             MeasurementMode.AT_MOST -> MeasureSpec.AT_MOST
             MeasurementMode.EXACTLY -> MeasureSpec.EXACTLY
            }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

            val result = applyGridLayout(
                container,
                container.children.filterIsInstance<Cell>(),
                MeasurementMode.EXACTLY,
                context.pxToPt(MeasureSpec.getSize(widthMeasureSpec)),
                MeasurementMode.EXACTLY,
                context.pxToPt(MeasureSpec.getSize(heightMeasureSpec)),
            /* measureOnly= */ false);

            setMeasuredDimension(context.ptToPx(result.first), context.ptToPx(result.second));
    }


    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {

        for (i in 0 until childCount) {
            val child = getChildAt(i)
            val layoutParams = child.layoutParams as LayoutParams

            val xPos = context.ptToPx(layoutParams.position.x)
            val yPos = context.ptToPx(layoutParams.position.y)

            child.layout(xPos, yPos, xPos + child.measuredWidth, yPos + child.measuredHeight)
        }
    }

    inner class LayoutParams(
        val position: Position
    ) : ViewGroup.LayoutParams(0, 0) {

    }

}