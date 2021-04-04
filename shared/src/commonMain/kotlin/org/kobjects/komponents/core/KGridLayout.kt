package org.kobjects.komponents.core

expect class KGridLayout(kontext: Kontext) : KView, Iterable<GridArea> {

    var columnGap: Double
    var rowGap: Double

    var autoColumns: Size
    var autoRows: Size

    var paddingLeft: Double
    var paddingTop: Double
    var paddingRight: Double
    var paddingBottom: Double

    var alignContent: Align
    var justifyContent: Align

    var alignItems: Align
    var justifyItems: Align

    var columnTemplate: List<Size>
    var rowTemplate: List<Size>

    val size: Int

    fun add(positioned: GridArea)

    fun get(index: Int): GridArea

    fun notifyAreaChanged(area: GridArea)

}