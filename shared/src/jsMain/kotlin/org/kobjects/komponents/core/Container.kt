package org.kobjects.komponents.core

import org.w3c.dom.HTMLDivElement
import org.w3c.dom.HTMLElement

actual class Container  actual constructor(kontext: Kontext) : KView() {

    actual var columnGap = 0.0
    actual var rowGap = 0.0
    actual var paddingLeft = 0.0
    actual var paddingTop = 0.0
    actual var paddingBottom = 0.0
    actual var paddingRight = 0.0
    actual var horizontalAlign = Align.START
    actual var verticalAlign = Align.START

    private val columnWidths = mutableListOf<Size?>()
    private val rowHeights = mutableListOf<Size?>()

    val children = mutableListOf<Positioned>()

    private val div = kontext.document.createElement("div") as HTMLDivElement

    init {
        div.style.display = "grid"
    }

    override fun getElement(): HTMLElement {
       return div
    }

    actual fun add(positioned: Positioned) {
        children.add(positioned)
        val element = positioned.component.getElement()
        div.appendChild(element)


    }

    actual var defaultColumnWidth: Size = Size.AUTO

    actual var defaultRowHeight: Size = Size.AUTO

    actual fun setColumnWidth(index: Int, width: Size?, repeat: Int) {
        while (columnWidths.size < index + repeat) {
            columnWidths.add(null)
        }
        for (i in index until index + repeat) {
            columnWidths[i] = width
        }
    }

    actual fun setRowHeight(index: Int, height: Size?, repeat: Int) {
        while (rowHeights.size < index + repeat) {
            rowHeights.add(null)
        }
        for (i in index until index + repeat) {
            rowHeights[i] = height
        }
    }

    actual fun getColumnWidth(index: Int): Size {
        return if (index >= columnWidths.size) defaultColumnWidth else columnWidths[index] ?: defaultColumnWidth
    }

    actual fun getRowHeight(index: Int): Size {
        return if (index >= rowHeights.size) defaultRowHeight else rowHeights[index] ?: defaultRowHeight
    }



}