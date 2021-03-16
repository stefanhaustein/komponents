package org.kobjects.komponents.core

expect class Container(kontext: Kontext) : KView {

    var columnGap: Double
    var rowGap: Double

    var defaultColumnWidth: Size
    var defaultRowHeight: Size

    fun setColumnWidth(index: Int, width: Size?, repeat: Int = 1)
    fun setRowHeight(index: Int, height: Size?, repeat: Int = 1)

    fun getColumnWidth(index: Int): Size
    fun getRowHeight(index: Int): Size

    fun add(positioned: Positioned)



}