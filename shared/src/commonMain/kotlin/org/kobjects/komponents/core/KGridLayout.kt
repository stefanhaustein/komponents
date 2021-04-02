package org.kobjects.komponents.core

expect class KGridLayout(kontext: Kontext) : KView {

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

    fun setColumnWidth(index: Int, width: Size?, repeat: Int = 1)
    fun setRowHeight(index: Int, height: Size?, repeat: Int = 1)

    fun templateColumnCount(): Int
    fun templateRowCount(): Int

    fun getColumnWidth(index: Int): Size
    fun getRowHeight(index: Int): Size

    fun add(positioned: GridArea)

    fun notifyAreaChanged(area: GridArea)

}