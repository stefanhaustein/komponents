package org.kobjects.komponents.core

expect class Positioned (
    component: KView,
    column: Int = 0,
    row: Int = 0,
    columnSpan: Int = 1,
    rowSpan: Int = 1,
    width: Double? = null,
    height: Double? = null,
    verticalAlign: Align = Align.STRETCH,
    horizontalAlign: Align = Align.STRETCH) {

    val component: KView
    var column: Int
    var columnSpan: Int
    var row: Int
    var rowSpan: Int
    var width: Double?
    var height: Double?
    var verticalAlign: Align
    var horizontalAlign: Align


}