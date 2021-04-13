package org.kobjects.komponents.core.grid

import org.kobjects.komponents.core.KView
import org.kobjects.komponents.core.Kontext
import org.w3c.dom.HTMLDivElement
import org.w3c.dom.HTMLElement

actual class KGridLayout  actual constructor(kontext: Kontext) : KView(), Iterable<Cell> {

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
    actual val size: Int
        get() = children.size
    actual var gap: Double?
        get() = if (columnGap == rowGap) columnGap else null
        set(value) {
            if (value != null) {
                columnGap = value
                rowGap = value
            }
        }
    actual var padding: Double?
        get() = if (paddingLeft == paddingRight && paddingRight == paddingTop && paddingTop == paddingBottom) paddingLeft else null
        set(value) {
            if (value != null) {
                paddingLeft = value
                paddingRight = value
                paddingTop = value
                paddingBottom = value
            }
        }

    val children = mutableListOf<Cell>()

    private val div = kontext.document.createElement("div") as HTMLDivElement

    init {
        div.style.display = "grid"
        justifyContent = Align.START // Html default seems to be stretch...
        alignContent = Align.START
    }

    override fun getElement(): HTMLElement {
       return div
    }

    actual fun addCell(
        view: KView,
        column: Int?,
        row: Int?,
        columnSpan: Int,
        rowSpan: Int,
        width: Double?,
        height: Double?,
        align: Align?,
        justify: Align?): Cell {
        val cell = Cell(view, column, row, columnSpan, rowSpan, width, height, align, justify)
        addCell(cell)
        return cell
    }

    actual fun addCell(positioned: Cell) {
        positioned.gridLayout = this
        children.add(positioned)
        div.appendChild(positioned.view.getElement())
        notifyPositionChanged(positioned)
    }

    actual fun notifyPositionChanged(position: Position) {
        val cell = position as Cell
        val style = position.view.getElement().style
        val column = position.column
        val row = position.row
        val width = position.width
        val height = position.height
        style.setProperty(
            "grid-column-start",
            if (column != null) "${column + 1}" else "")
        style.setProperty(
            "grid-column-end",
            "span ${position.columnSpan}")
        style.setProperty(
            "grid-row-start",
            if (row != null) "${row + 1}" else "")
        style.setProperty(
            "grid-row-end",
            "span ${position.rowSpan}" )
        style.width = if (width != null) "${width}px" else ""
        style.height = if (height != null) "${width}px" else ""
        style.setProperty(
            "align-self",
            position.verticalAlign?.name?.toLowerCase() ?: ""
        )
        style.setProperty(
            "justify-self",
            position.horizontalAlign?.name?.toLowerCase() ?: ""
        )

        // ${(area.horizontalAlign.name.toLowerCase()}"
    }


    actual fun getCell(index: Int): Cell = children[index]

    override fun iterator(): Iterator<Cell> {
        return children.iterator()
    }

}