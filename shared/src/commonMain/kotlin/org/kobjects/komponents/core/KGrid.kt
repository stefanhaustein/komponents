package org.kobjects.komponents.core

expect class KGrid(kontext: Kontext) : KView {

    var columnGap: Double
    var rowGap: Double

    var defaultColumnWidth: Size
    var defaultRowHeight: Size

    var paddingLeft: Double
    var paddingTop: Double
    var paddingRight: Double
    var paddingBottom: Double

    var verticalAlign: Align
    var horizontalAlign: Align

    fun setColumnWidth(index: Int, width: Size?, repeat: Int = 1)
    fun setRowHeight(index: Int, height: Size?, repeat: Int = 1)

    fun getColumnWidth(index: Int): Size
    fun getRowHeight(index: Int): Size

    fun add(positioned: Positioned)



}