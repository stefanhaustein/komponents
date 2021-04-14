package org.kobjects.komponents.core.grid.mobile

import org.kobjects.komponents.core.grid.Align
import org.kobjects.komponents.core.grid.Cell
import org.kobjects.komponents.core.grid.KGridLayout
import org.kobjects.komponents.core.grid.Size

enum class MeasurementMode {
    UNSPECIFIED, AT_MOST, EXACTLY
}

fun sum(doubles: DoubleArray, start: Int, count: Int): Double {
    var sum = 0.0
    for (i in 0 until count) {
        sum += doubles[start + i]
    }
    return sum
}

private fun getColumnWidth(grid: KGridLayout, column: Int) =
    grid.columnTemplate.getOrElse(column) { grid.autoColumns }


private fun getRowHeight(grid: KGridLayout, row: Int) =
    grid.rowTemplate.getOrElse(row) { grid.autoRows }


fun applyGridLayout(
    container: KGridLayout,
    children: List<Cell>,
    widthMode: MeasurementMode,
    inputWidth: Double,
    heightMode: MeasurementMode,
    inputHeight: Double,
    measureOnly: Boolean
): Pair<Double, Double> {

    var rowCount = container.rowTemplate.size
    var columnCount = container.columnTemplate.size

    // Determine size

    var filled = BitSet2D()
    var unpositioned = mutableListOf<Cell>()

    for (child in children) {
        child as ResolvedPosition

        val childColumn = child.column
        val childRow = child.row
        if (childColumn != null && childRow != null) {
            child.resolvedColumn = childColumn
            child.resolvedRow = childRow
            columnCount = maxOf(columnCount, child.resolvedColumn + child.columnSpan)
            rowCount = maxOf(rowCount, child.resolvedRow + child.rowSpan)
            fill(filled, child)
        } else {
            unpositioned.add(child)
        }
    }

    var row = 0
    var col = 0

    for (child in unpositioned) {
        child as ResolvedPosition
        while(true) {
            var positionSuitable = true
            outer@ for (r in row until row + child.rowSpan) {
                for (c in col until col + child.columnSpan) {
                    if (filled.get(r, c)) {
                        positionSuitable = false
                        break@outer
                    }
                }
            }
            if (positionSuitable) {
                break
            }
            if (col + child.columnSpan < columnCount) {
                col++
            } else {
                row++
                col = 0
            }
        }
        // Found a suitable child pos at row/col
        child.resolvedColumn = col
        child.resolvedRow = row
        columnCount = maxOf(columnCount, child.resolvedColumn + child.columnSpan)
        rowCount = maxOf(rowCount, child.resolvedRow + child.rowSpan)
        fill(filled, child)
    }

    val widths = DoubleArray(columnCount) { 0.0 }
    val heights = DoubleArray(rowCount) { 0.0 }

    // Measure auto columns / rows

    // Collect cases where bothe, the width and height are auto. In these cases,
    // re-measure the height after the widths were established.
    val reMeasure = mutableListOf<ResolvedPosition>()

    for (child in children) {
        child as ResolvedPosition

        var childWidthMode = MeasurementMode.UNSPECIFIED;
        var childHeightMode = MeasurementMode.UNSPECIFIED;
        var childWidth = 0.0
        var childHeight = 0.0
        var measurementRequired = false
        var updateWidth = false
        var updateHeight = false

        if (child.columnSpan == 1 && getColumnWidth(container, child.resolvedColumn) == Size.AUTO) {
            updateWidth = true
            if (child.width != null) {
                childWidth = child.width ?: 0.0
                childWidthMode = MeasurementMode.EXACTLY
            } else {
                measurementRequired = true
            }
        }
        if (child.rowSpan == 1 && getRowHeight(container, child.resolvedRow) == Size.AUTO) {
            updateHeight = true
            if (child.height != null) {
                childHeight = child.height ?: 0.0
                childHeightMode = MeasurementMode.EXACTLY
            } else {
                measurementRequired = true
            }
        }
        if (measurementRequired) {
            child.layout(childWidthMode, childWidth, childHeightMode, childHeight, true)
            childWidth = child.measuredWidth()
            childHeight = child.measuredHeight()
            if (childWidthMode == MeasurementMode.UNSPECIFIED
                && childHeightMode == MeasurementMode.UNSPECIFIED) {
                println("adding for re-measure")
                reMeasure.add(child)
            }
        }
        if (updateWidth) {
            widths[child.resolvedColumn] = maxOf(widths[child.resolvedColumn], childWidth)
        }
        if (updateHeight) {
            heights[child.resolvedRow] = maxOf(heights[child.resolvedRow], childHeight)
        }
    }

  //  System.out.println("### widths: " + Arrays.toString(widths))
  //  System.out.println("### heights: " + Arrays.toString(heights))

    var consumedWidth = container.paddingLeft + maxOf(columnCount - 1, 0) * container.columnGap + container.paddingRight
    var totalHorizontalFr = 0.0
    for (i in 0 until columnCount) {
        val columnWith = getColumnWidth(container, i)
        when (columnWith.unit) {
            Size.Unit.PX -> widths[i] = columnWith.value
            Size.Unit.FR -> totalHorizontalFr += columnWith.value
        }
        consumedWidth += widths[i]
    }

    // Distribute extra width

    var remainingWidth = 0.0
    if (widthMode == MeasurementMode.EXACTLY) {
        val available = inputWidth - consumedWidth
        // System.out.println("### total Width: $totalWidth - measrued: ${MeasureSpec.getSize(widthMeasureSpec)} = available: $available")
        if (available > 0) {
            if (totalHorizontalFr == 0.0) {
                remainingWidth = available
            } else {
                for (i in 0 until columnCount) {
                    val columnWidth = getColumnWidth(container, i)
                    if (columnWidth.unit == Size.Unit.FR) {
                        widths[i] = available * columnWidth.value / totalHorizontalFr
                        consumedWidth += widths[i]
                    }
                }
            }
        }
    }

    println("children to re-measure: $reMeasure")

    for (child in reMeasure) {
        child.layout(
            MeasurementMode.EXACTLY,
            widths[child.resolvedColumn],
            MeasurementMode.UNSPECIFIED,
            0.0,
            true)
        heights[child.resolvedRow] = maxOf(heights[child.resolvedRow], child.measuredHeight())
    }

    var consumedHeight = container.paddingTop + maxOf(rowCount - 1, 0) * container.rowGap + container.paddingBottom
    var totalVerticalFr = 0.0
    for (i in 0 until rowCount) {
        val rowHeight = getRowHeight(container, i)
        when (rowHeight.unit) {
            Size.Unit.PX -> heights[i] = rowHeight.value
            Size.Unit.FR -> totalVerticalFr += rowHeight.value
        }
        consumedHeight += heights[i]
    }

   // System.out.println("### total Width: $totalWidth height: $totalHeight")


    var remainingHeight = 0.0
    if (heightMode == MeasurementMode.EXACTLY) {
        val available = inputHeight - consumedHeight
        if (totalVerticalFr == 0.0) {
            remainingHeight = available
        } else {
            // System.out.println("### total height: $totalHeight - measrued: ${MeasureSpec.getSize(heightMeasureSpec)} = available: $available")
            if (available > 0) {
                for (i in 0 until rowCount) {
                    val rowHeight = getRowHeight(container, i)
                    if (rowHeight.unit == Size.Unit.FR) {
                        heights[i] = available * rowHeight.value / totalVerticalFr
                        consumedHeight += heights[i]
                    }
                }
            }
        }
    }

    if (measureOnly) {
        return Pair(consumedWidth + remainingWidth, consumedHeight + remainingHeight)
    }


    val xPositions = DoubleArray(columnCount + 1) {0.0}
    xPositions[0] = container.paddingLeft + when (container.justifyContent) {
        Align.END -> remainingWidth
        Align.CENTER -> remainingWidth / 2
        else -> 0.0
    }
    for (i in widths.indices) {
        xPositions[i + 1] = xPositions[i] + widths[i] + container.columnGap
    }
    val yPositions = DoubleArray(rowCount + 1) {0.0}
    yPositions[0] = container.paddingTop + when (container.alignContent) {
        Align.END -> remainingHeight
        Align.CENTER -> remainingHeight / 2
        else -> 0.0
    }
    for (i in heights.indices) {
        yPositions[i + 1] = yPositions[i] + heights[i] + container.rowGap
    }

    for (child in children) {
        child as ResolvedPosition

        val alignChild = child.verticalAlign ?: container.alignItems
        val justifyChild = child.horizontalAlign ?: container.justifyItems

        val childWidthMode = if (child.width != null || justifyChild == Align.STRETCH) MeasurementMode.EXACTLY else MeasurementMode.AT_MOST
        val childHeightMode = if (child.height != null || alignChild == Align.STRETCH) MeasurementMode.EXACTLY else MeasurementMode.AT_MOST

        var childWidth = if (child.width != null) (child.width ?: 0.0) else sum(widths, child.resolvedColumn, child.columnSpan)
        var childHeight = if (child.height != null) (child.height ?: 0.0) else sum(heights, child.resolvedRow, child.rowSpan)

        child.layout(childWidthMode, childWidth, childHeightMode, childHeight, false)
        childWidth = child.measuredWidth()
        childHeight = child.measuredHeight()

        var xPos = xPositions[child.resolvedColumn]
        when (child.horizontalAlign ?: container.justifyItems) {
            Align.CENTER -> xPos += (widths[child.resolvedColumn] - childWidth) / 2
            Align.END -> xPos += widths[child.resolvedColumn] - childWidth
        }
        var yPos = yPositions[child.resolvedRow]
        when (child.verticalAlign ?: container.alignItems) {
            Align.CENTER -> yPos += (heights[child.resolvedRow] - childHeight) / 2
            Align.END -> yPos += heights[child.resolvedRow] - childHeight
        }
        child.setPosition(xPos, yPos)
    }


    // System.err.println("### xPos: " + Arrays.toString(xPositions))
    // System.err.println("### yPos: " + Arrays.toString(yPositions))

    // System.out.println("### total Width: $totalWidth height: $totalHeight")

    // System.err.println("**************************** Measured dimensions: " + totalWidth + " x " + totalHeight)

    return Pair(consumedWidth + remainingWidth, consumedHeight + remainingHeight)
}

fun fill(filled: BitSet2D, resolvedPosition: Cell) {
    resolvedPosition as ResolvedPosition
    for (r in 0 until resolvedPosition.rowSpan) {
        for (c in 0 until resolvedPosition.columnSpan) {
            filled.set(resolvedPosition.resolvedRow + r, resolvedPosition.resolvedColumn + c, true)
        }
    }
}