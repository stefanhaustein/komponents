package org.kobjects.komponents.mobile

import org.kobjects.komponents.core.Align
import org.kobjects.komponents.core.Container
import org.kobjects.komponents.core.Positioned
import org.kobjects.komponents.core.Size


enum class MeasurementMode {
    UNSPECIFIED, AT_MOST, EXACTLY
}


interface ChildLayout {
    val positioned: Positioned

    fun measure(widthMode: MeasurementMode, width: Double, heightMode: MeasurementMode, height: Double): Pair<Double, Double>

    fun setPosition(x: Double, y: Double)



}




fun applyGridLayout(
    container: Container,
    children: List<ChildLayout>,
    widthMode: MeasurementMode,
    inputWidth: Double,
    heightMode: MeasurementMode,
    inputHeight: Double,
): Pair<Double, Double> {


//    System.err.println("************** onMeaseure ${MeasureSpec.getMode(widthMeasureSpec)}/${MeasureSpec.getSize(widthMeasureSpec)} / ${MeasureSpec.getMode(heightMeasureSpec)}/${MeasureSpec.getSize(heightMeasureSpec)}")

    var rowCount = 0
    var columnCount = 0

    // Determine size

    for (child in children) {
        val positioned = child.positioned

        columnCount = maxOf(columnCount, positioned.column + positioned.columnSpan)
        rowCount = maxOf(rowCount, positioned.row + positioned.rowSpan)
    }

    val widths = DoubleArray(columnCount) { 0.0 }
    val heights = DoubleArray(rowCount) { 0.0 }

    // Measure auto columns / rows

    for (child in children) {
        val positioned = child.positioned

        var childWidthMode = MeasurementMode.UNSPECIFIED;
        var childHeightMode = MeasurementMode.UNSPECIFIED;
        var childWidth = 0.0
        var childHeight = 0.0
        var measurementRequired = false
        var updateWidth = false
        var updateHeight = false

        if (container.getColumnWidth(positioned.column) == Size.AUTO && positioned.columnSpan == 1) {
            updateWidth = true
            if (positioned.width != null) {
                childWidth = positioned.width ?: 0.0
                childWidthMode = MeasurementMode.EXACTLY
            } else {
                measurementRequired = true;
            }
        }
        if (container.getRowHeight(positioned.row) == Size.AUTO && positioned.rowSpan == 1) {
            updateHeight = true
            if (positioned.height != null) {
                childHeight = positioned.height ?: 0.0
                childHeightMode = MeasurementMode.EXACTLY
            } else {
                measurementRequired = true;
            }
        }
        if (measurementRequired) {
            val result = child.measure(childWidthMode, childWidth, childHeightMode, childHeight)
            childWidth = result.first
            childHeight = result.second
        }
        if (updateWidth) {
            widths[positioned.column] = maxOf(widths[positioned.column], childWidth)
        }
        if (updateHeight) {
            heights[positioned.row] = maxOf(heights[positioned.row], childHeight)
        }
    }

  //  System.out.println("### widths: " + Arrays.toString(widths))
  //  System.out.println("### heights: " + Arrays.toString(heights))

    var totalWidth = maxOf(columnCount - 1, 0) * container.columnGap
    var totalHorizontalFr = 0.0
    for (i in 0 until columnCount) {
        var columnWith = container.getColumnWidth(i)
        when (columnWith.unit) {
            Size.Unit.PX -> widths[i] = columnWith.value
            Size.Unit.FR -> totalHorizontalFr += columnWith.value
        }
        totalWidth += widths[i]
    }

    var totalHeight = maxOf(rowCount - 1, 0) * container.rowGap
    var totalVerticalFr = 0.0
    for (i in 0 until rowCount) {
        var rowHeight = container.getRowHeight(i)
        when (rowHeight.unit) {
            Size.Unit.PX -> widths[i] = rowHeight.value
            Size.Unit.FR -> totalVerticalFr += rowHeight.value
        }
        totalHeight += heights[i]
    }

   // System.out.println("### total Width: $totalWidth height: $totalHeight")

    // Distribute extra size

    if (widthMode == MeasurementMode.EXACTLY && totalHorizontalFr > 0) {
        val available = inputWidth - totalWidth
        // System.out.println("### total Width: $totalWidth - measrued: ${MeasureSpec.getSize(widthMeasureSpec)} = available: $available")
        if (available > 0) {
            for (i in 0 until columnCount) {
                val columnWidth = container.getColumnWidth(i)
                if (columnWidth.unit == Size.Unit.FR) {
                    widths[i] = available * columnWidth.value / totalHorizontalFr
                    totalWidth += widths[i]
                }
            }
        }
    }

    if (heightMode == MeasurementMode.EXACTLY && totalVerticalFr > 0) {
        val available = inputHeight - totalHeight
        // System.out.println("### total height: $totalHeight - measrued: ${MeasureSpec.getSize(heightMeasureSpec)} = available: $available")
        if (available > 0) {
            for (i in 0 until rowCount) {
                val rowHeight = container.getRowHeight(i)
                if (rowHeight.unit == Size.Unit.FR) {
                    heights[i] = available * rowHeight.value / totalHorizontalFr
                    totalHeight += heights[i]
                }
            }
        }
    }

    val xPositions = DoubleArray(columnCount + 1) {0.0}
    val yPositions = DoubleArray(rowCount + 1) {0.0}
    for (i in widths.indices) {
        xPositions[i + 1] = xPositions[i] + widths[i] + container.columnGap
    }
    for (i in heights.indices) {
        yPositions[i + 1] = yPositions[i] + heights[i] + container.rowGap
    }

    for (child in children) {
        val positioned = child.positioned

        val childWidthMode = if (positioned.width != null || positioned.horizontalAlign == Align.STRETCH) MeasurementMode.EXACTLY else MeasurementMode.AT_MOST
        val childHeightMode = if (positioned.height != null || positioned.verticalAlign == Align.STRETCH) MeasurementMode.EXACTLY else MeasurementMode.AT_MOST

        var childWidth = if (positioned.width != null) (positioned.width ?: 0.0) else widths[positioned.column]
        var childHeight = if (positioned.height != null) (positioned.height ?: 0.0) else heights[positioned.row]

        val result = child.measure(childWidthMode, childWidth, childHeightMode, childHeight)
        childWidth = result.first
        childHeight = result.second

        var xPos = xPositions[positioned.column]
        when (positioned.horizontalAlign) {
            Align.CENTER -> xPos += (widths[positioned.column] - childWidth) / 2
            Align.END -> xPos += widths[positioned.column] - childWidth
        }
        var yPos = yPositions[positioned.row]
        when (positioned.verticalAlign) {
            Align.CENTER -> yPos += (heights[positioned.row] - childHeight) / 2
            Align.END -> yPos += heights[positioned.row] - childHeight
        }
        child.setPosition(xPos, yPos)
    }


    // System.err.println("### xPos: " + Arrays.toString(xPositions))
    // System.err.println("### yPos: " + Arrays.toString(yPositions))

    // System.out.println("### total Width: $totalWidth height: $totalHeight")

    // System.err.println("**************************** Measured dimensions: " + totalWidth + " x " + totalHeight)

    return Pair(totalWidth, totalHeight)
}