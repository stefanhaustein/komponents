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
    actual var justifyContent = Align.START
        set(value) {
            field = value
            div.style.justifyContent = value.toString().toLowerCase()
        }
    actual var alignContent = Align.START
        set(value) {
            field = value
            div.style.alignContent = value.toString().toLowerCase()
        }

    actual var autoColumns = Size.AUTO
        set(value) {
            field = value
            div.style.setProperty("auto-columns", value.toString())}

    actual var autoRows = Size.AUTO
        set(value) {
            field = value
            div.style.setProperty("auto-rows", value.toString())
        }
    actual var alignItems = Align.STRETCH
        set(value) {
            field = value
            div.style.setProperty("align-items", value.toString())
        }
    actual var justifyItems = Align.STRETCH
        set(value) {
            field = value
            div.style.setProperty("justify-items", value.toString())
        }
    actual var columnTemplate = listOf<Size>()
        set(value) {
            field = value
            div.style.setProperty("grid-template-columns", value.joinToString(" "))
        }
    actual var rowTemplate = listOf<Size>()
        set(value) {
            field = value
            div.style.setProperty("grid-template-rows", value.joinToString(" "))
        }

    val children = mutableListOf<GridArea>()

    private val div = kontext.document.createElement("div") as HTMLDivElement

    init {
        div.style.display = "grid"
        justifyContent = Align.START // Html default seems to be stretch...
        alignContent = Align.START
    }

    override fun getElement(): HTMLElement {
       return div
    }

    actual fun add(positioned: GridArea) {
        positioned.gridLayout = this
        children.add(positioned)
        div.appendChild(positioned.view.getElement())
        notifyAreaChanged(positioned)
    }

    actual fun notifyAreaChanged(area: GridArea) {
        val style = area.view.getElement().style
        val column = area.column
        val row = area.row
        val width = area.width
        val height = area.height
        style.setProperty(
            "grid-column-start",
            if (column != null) "${column + 1}" else "")
        style.setProperty(
            "grid-column-end",
            "span ${area.columnSpan}")
        style.setProperty(
            "grid-row-start",
            if (row != null) "${row + 1}" else "")
        style.setProperty(
            "grid-row-end",
            "span ${area.rowSpan}" )
        style.width = if (width != null) "${width}px" else ""
        style.height = if (height != null) "${width}px" else ""
        style.setProperty(
            "align-self",
            area.verticalAlign?.name?.toLowerCase() ?: ""
        )
        style.setProperty(
            "justify-self",
            area.horizontalAlign?.name?.toLowerCase() ?: ""
        )

        // ${(area.horizontalAlign.name.toLowerCase()}"
    }

}