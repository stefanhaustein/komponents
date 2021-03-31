package org.kobjects.komponents.core

actual class GridArea actual constructor(
    view: KView,
    column: Int?,
    row: Int?,
    columnSpan: Int,
    rowSpan: Int,
    width: Double?,
    height: Double?,
    verticalAlign: Align,
    horizontalAlign: Align
) {
    actual val view: KView = view
    actual var column: Int? = column
    actual var columnSpan: Int = columnSpan
    actual var row: Int? = row
    actual var rowSpan: Int = rowSpan
    actual var width: Double? = width
    actual var height: Double? = height
    actual var verticalAlign: Align = verticalAlign
    actual var horizontalAlign: Align = horizontalAlign
}