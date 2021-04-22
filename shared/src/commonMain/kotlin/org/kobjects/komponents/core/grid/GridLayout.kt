package org.kobjects.komponents.core.grid

import org.kobjects.komponents.core.Widget
import org.kobjects.komponents.core.Context

expect class GridLayout(context: Context) : Widget, Iterable<Position> {

    var columnGap: Double
    var rowGap: Double
    var gap: Double?

    var autoColumns: Size
    var autoRows: Size

    var paddingLeft: Double
    var paddingTop: Double
    var paddingRight: Double
    var paddingBottom: Double
    var padding: Double?

    var alignContent: Align
    var justifyContent: Align

    var alignItems: Align
    var justifyItems: Align

    var columnTemplate: List<Size>
    var rowTemplate: List<Size>

    val size: Int

    fun addCell(
        view: Widget,
        column: Int? = null,
        row: Int? = null,
        columnSpan: Int = 1,
        rowSpan: Int = 1,
        width: Double? = null,
        height: Double? = null,
        align: Align? = null,
        justify: Align? = null): Cell

    fun addAbsolute(
        view: Widget,
        top: Double? = null,
        right: Double? = null,
        bottom: Double? = null,
        left: Double? = null,
        width: Double? = null,
        height: Double? = null): Absolute

    fun get(index: Int): Position

    fun remove(widget: Widget)
}