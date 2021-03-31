package org.kobjects.komponents.core.mobile

import org.kobjects.komponents.core.Align
import org.kobjects.komponents.core.KGridLayout
import org.kobjects.komponents.core.GridArea
import org.kobjects.komponents.core.Size

enum class MeasurementMode {
    UNSPECIFIED, AT_MOST, EXACTLY
}


interface ChildLayout {
    val positioned: GridArea
    var column: Int
    var row: Int


    fun measure(widthMode: MeasurementMode, width: Double, heightMode: MeasurementMode, height: Double)

    fun measuredWidth(): Double
    fun measuredHeight(): Double

    fun setPosition(x: Double, y: Double)

}




fun applyGridLayout(
    container: KGridLayout,
    children: List<ChildLayout>,
    widthMode: MeasurementMode,
    inputWidth: Double,
    heightMode: MeasurementMode,
    inputHeight: Double,
): Pair<Double, Double> {


//    System.err.println("************** onMeaseure ${MeasureSpec.getMode(widthMeasureSpec)}/${MeasureSpec.getSize(widthMeasureSpec)} / ${MeasureSpec.getMode(heightMeasureSpec)}/${MeasureSpec.getSize(heightMeasureSpec)}")

    // Todo: Use template
    var rowCount = 0
    var columnCount = 0

    // Determine size

    var filled = BitSet2D()
    var unpositioned = mutableListOf<ChildLayout>()

    for (child in children) {
        val positioned = child.positioned

        val positionedColumn = positioned.column
        val positionedRow = positioned.row
        if (positionedColumn != null && positionedRow != null) {
            child.column = positionedColumn
            child.row = positionedRow
            columnCount = maxOf(columnCount, child.column + positioned.columnSpan)
            rowCount = maxOf(rowCount, child.row + positioned.rowSpan)
            fill(filled, child)
        } else {
            unpositioned.add(child)
        }
    }

    var row = 0
    var col = 0

    for (child in unpositioned) {
        var positioned = child.positioned
        while(true) {
            var positionSuitable = true
            outer@ for (r in row until row + positioned.rowSpan) {
                for (c in col until col + positioned.columnSpan) {
                    if (filled.get(r, c)) {
                        positionSuitable = false
                        break@outer
                    }
                }
            }
            if (positionSuitable) {
                break
            }
            if (col + positioned.columnSpan < columnCount) {
                col++
            } else {
                row++
            }
        }
        // Found a suitable child pos at row/col
        child.column = col
        child.row = row
        columnCount = maxOf(columnCount, child.column + positioned.columnSpan)
        rowCount = maxOf(rowCount, child.row + positioned.rowSpan)
        fill(filled, child)
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

        if (container.getColumnWidth(child.column) == Size.AUTO && positioned.columnSpan == 1) {
            updateWidth = true
            if (positioned.width != null) {
                childWidth = positioned.width ?: 0.0
                childWidthMode = MeasurementMode.EXACTLY
            } else {
                measurementRequired = true;
            }
        }
        if (container.getRowHeight(child.row) == Size.AUTO && positioned.rowSpan == 1) {
            updateHeight = true
            if (positioned.height != null) {
                childHeight = positioned.height ?: 0.0
                childHeightMode = MeasurementMode.EXACTLY
            } else {
                measurementRequired = true;
            }
        }
        if (measurementRequired) {
            child.measure(childWidthMode, childWidth, childHeightMode, childHeight)
            childWidth = child.measuredWidth()
            childHeight = child.measuredHeight()
        }
        if (updateWidth) {
            widths[child.column] = maxOf(widths[child.column], childWidth)
        }
        if (updateHeight) {
            heights[child.row] = maxOf(heights[child.row], childHeight)
        }
    }

  //  System.out.println("### widths: " + Arrays.toString(widths))
  //  System.out.println("### heights: " + Arrays.toString(heights))

    var consumedWidth = container.paddingLeft + maxOf(columnCount - 1, 0) * container.columnGap + container.paddingRight
    var totalHorizontalFr = 0.0
    for (i in 0 until columnCount) {
        val columnWith = container.getColumnWidth(i)
        when (columnWith.unit) {
            Size.Unit.PX -> widths[i] = columnWith.value
            Size.Unit.FR -> totalHorizontalFr += columnWith.value
        }
        consumedWidth += widths[i]
    }

    var consumedHeight = container.paddingTop + maxOf(rowCount - 1, 0) * container.rowGap + container.paddingBottom
    var totalVerticalFr = 0.0
    for (i in 0 until rowCount) {
        val rowHeight = container.getRowHeight(i)
        when (rowHeight.unit) {
            Size.Unit.PX -> widths[i] = rowHeight.value
            Size.Unit.FR -> totalVerticalFr += rowHeight.value
        }
        consumedHeight += heights[i]
    }

   // System.out.println("### total Width: $totalWidth height: $totalHeight")

    // Distribute extra size

    var remainingWidth = 0.0
    if (widthMode == MeasurementMode.EXACTLY) {
        val available = inputWidth - consumedWidth
        // System.out.println("### total Width: $totalWidth - measrued: ${MeasureSpec.getSize(widthMeasureSpec)} = available: $available")
        if (available > 0) {
            if (totalHorizontalFr == 0.0) {
                remainingWidth = available
            } else {
                for (i in 0 until columnCount) {
                    val columnWidth = container.getColumnWidth(i)
                    if (columnWidth.unit == Size.Unit.FR) {
                        widths[i] = available * columnWidth.value / totalHorizontalFr
                        consumedWidth += widths[i]
                    }
                }
            }
        }
    }

    var remainingHeight = 0.0
    if (heightMode == MeasurementMode.EXACTLY) {
        val available = inputHeight - consumedHeight
        if (totalVerticalFr == 0.0) {
            remainingHeight = available
        } else {
            // System.out.println("### total height: $totalHeight - measrued: ${MeasureSpec.getSize(heightMeasureSpec)} = available: $available")
            if (available > 0) {
                for (i in 0 until rowCount) {
                    val rowHeight = container.getRowHeight(i)
                    if (rowHeight.unit == Size.Unit.FR) {
                        heights[i] = available * rowHeight.value / totalVerticalFr
                        consumedHeight += heights[i]
                    }
                }
            }
        }
    }

    val xPositions = DoubleArray(columnCount + 1) {0.0}
    xPositions[0] = container.paddingLeft + when (container.horizontalAlign) {
        Align.END -> remainingWidth
        Align.CENTER -> remainingWidth / 2
        else -> 0.0
    }
    for (i in widths.indices) {
        xPositions[i + 1] = xPositions[i] + widths[i] + container.columnGap
    }
    val yPositions = DoubleArray(rowCount + 1) {0.0}
    yPositions[0] = container.paddingTop + when (container.verticalAlign) {
        Align.END -> remainingHeight
        Align.CENTER -> remainingHeight / 2
        else -> 0.0
    }
    for (i in heights.indices) {
        yPositions[i + 1] = yPositions[i] + heights[i] + container.rowGap
    }

    for (child in children) {
        val positioned = child.positioned

        val childWidthMode = if (positioned.width != null || positioned.horizontalAlign == Align.STRETCH) MeasurementMode.EXACTLY else MeasurementMode.AT_MOST
        val childHeightMode = if (positioned.height != null || positioned.verticalAlign == Align.STRETCH) MeasurementMode.EXACTLY else MeasurementMode.AT_MOST

        var childWidth = if (positioned.width != null) (positioned.width ?: 0.0) else widths[child.column]
        var childHeight = if (positioned.height != null) (positioned.height ?: 0.0) else heights[child.row]

        child.measure(childWidthMode, childWidth, childHeightMode, childHeight)
        childWidth = child.measuredWidth()
        childHeight = child.measuredHeight()

        var xPos = xPositions[child.column]
        when (positioned.horizontalAlign) {
            Align.CENTER -> xPos += (widths[child.column] - childWidth) / 2
            Align.END -> xPos += widths[child.column] - childWidth
        }
        var yPos = yPositions[child.row]
        when (positioned.verticalAlign) {
            Align.CENTER -> yPos += (heights[child.row] - childHeight) / 2
            Align.END -> yPos += heights[child.row] - childHeight
        }
        child.setPosition(xPos, yPos)
    }


    // System.err.println("### xPos: " + Arrays.toString(xPositions))
    // System.err.println("### yPos: " + Arrays.toString(yPositions))

    // System.out.println("### total Width: $totalWidth height: $totalHeight")

    // System.err.println("**************************** Measured dimensions: " + totalWidth + " x " + totalHeight)

    return Pair(consumedWidth + remainingWidth, consumedHeight + remainingHeight)
}

fun fill(filled: BitSet2D, childLayout: ChildLayout) {
    for (r in 0 until childLayout.positioned.rowSpan) {
        for (c in 0 until childLayout.positioned.columnSpan) {
            filled.set(childLayout.row + r, childLayout.column + c, true)
        }
    }
}