package org.kobjects.komponents.core.grid

import org.kobjects.komponents.core.KView
import org.kobjects.komponents.core.Kontext

expect class KGridLayout(kontext: Kontext) : KView, Iterable<Cell> {

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
        view: KView,
        column: Int? = null,
        row: Int? = null,
        columnSpan: Int = 1,
        rowSpan: Int = 1,
        width: Double? = null,
        height: Double? = null,
        align: Align? = null,
        justify: Align? = null): Cell


    fun addCell(positioned: Cell)

    fun getCell(index: Int): Cell

    fun notifyPositionChanged(position: Position)
}