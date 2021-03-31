package org.kobjects.komponents.core

expect class GridArea (
    view: KView,
    column: Int? = null,
    row: Int? = null,
    columnSpan: Int = 1,
    rowSpan: Int = 1,
    width: Double? = null,
    height: Double? = null,
    verticalAlign: Align = Align.STRETCH,
    horizontalAlign: Align = Align.STRETCH) {

    val view: KView
    var column: Int?
    var columnSpan: Int
    var row: Int?
    var rowSpan: Int
    var width: Double?
    var height: Double?
    var verticalAlign: Align
    var horizontalAlign: Align
}