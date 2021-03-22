package org.kobjects.komponents.core

import org.w3c.dom.HTMLDivElement
import org.w3c.dom.HTMLElement

actual class KGridLayout  actual constructor(kontext: Kontext) : KView() {

    actual var columnGap = 0.0
        set(value) {
            field = value
            div.style.columnGap = "${value}px"
        }
    actual var rowGap = 0.0
        set(value) {
            field = value
            div.style.setProperty("row-gap", "${value}px")
        }
    actual var paddingLeft = 0.0
        set(value) {
            field = value
            div.style.paddingLeft = "${value}px"
        }
    actual var paddingTop = 0.0
        set(value) {
            field = value
            div.style.paddingTop = "${value}px"
        }
    actual var paddingBottom = 0.0
        set(value) {
            field = value
            div.style.paddingBottom = "${value}px"
        }
    actual var paddingRight = 0.0
        set(value) {
            field = value
            div.style.paddingRight = "${value}px"
        }
    actual var horizontalAlign = Align.START
        set(value) {
            field = value
            div.style.justifyContent = value.toString().toLowerCase()
        }
    actual var verticalAlign = Align.START
        set(value) {
            field = value
            div.style.alignContent = value.toString().toLowerCase()
        }

    private val columnWidths = mutableListOf<Size?>()
    private val rowHeights = mutableListOf<Size?>()

    val children = mutableListOf<GridArea>()

    private val div = kontext.document.createElement("div") as HTMLDivElement

    init {
        div.style.display = "grid"
    }

    override fun getElement(): HTMLElement {
       return div
    }

    actual fun add(positioned: GridArea) {
        children.add(positioned)
        val element = positioned.view.getElement()
        div.appendChild(element)
    }

    actual var defaultColumnWidth: Size = Size.AUTO

    actual var defaultRowHeight: Size = Size.AUTO

    fun setSizes(propertyName: String, list: MutableList<Size?>, index: Int, width: Size?, repeat: Int) {
        while (list.size < index + repeat) {
            list.add(null)
        }
        for (i in index until index + repeat) {
            list[i] = width
        }
        val sb = StringBuilder()
        for (size in list) {
           if (sb.isNotEmpty()) {
               sb.append(' ')
           }
            if (size == null) {
                sb.append("auto")
            } else {
                sb.append(size)
            }
        }
        div.style.setProperty(propertyName, sb.toString())
    }

    actual fun setColumnWidth(index: Int, width: Size?, repeat: Int) {
        setSizes("grid-template-columns", columnWidths, index, width, repeat)
    }

    actual fun setRowHeight(index: Int, height: Size?, repeat: Int) {
        setSizes("grid-template-rows", rowHeights, index, height, repeat)
    }

    actual fun getColumnWidth(index: Int): Size {
        return if (index >= columnWidths.size) defaultColumnWidth else columnWidths[index] ?: defaultColumnWidth
    }

    actual fun getRowHeight(index: Int): Size {
        return if (index >= rowHeights.size) defaultRowHeight else rowHeights[index] ?: defaultRowHeight
    }



}