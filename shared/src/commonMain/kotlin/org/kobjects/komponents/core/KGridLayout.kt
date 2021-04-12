package org.kobjects.komponents.core

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

    val cellCount: Int

    fun add(positioned: Cell)

    fun getCell(index: Int): Cell

    fun notifyPositionChanged(position: Position)
}