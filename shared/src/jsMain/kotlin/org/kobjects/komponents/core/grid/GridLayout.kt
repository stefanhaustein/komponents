package org.kobjects.komponents.core.grid

import org.kobjects.komponents.core.Widget
import org.kobjects.komponents.core.Context
import org.w3c.dom.HTMLDivElement
import org.w3c.dom.HTMLElement

actual class GridLayout  actual constructor(context: Context) : Widget(), Iterable<Position> {

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

    val children = mutableListOf<Position>()

    private val div = context.document.createElement("div") as HTMLDivElement

    init {
        div.style.display = "grid"
        div.style.position = "relative"
        justifyContent = Align.START // Html default seems to be stretch...
        alignContent = Align.START
    }

    override fun getElement(): HTMLElement {
       return div
    }

    actual fun addCell(
        view: Widget,
        column: Int?,
        row: Int?,
        columnSpan: Int,
        rowSpan: Int,
        width: Double?,
        height: Double?,
        align: Align?,
        justify: Align?): Cell {
        view.parentImpl = this
        val cell = Cell(this, view, column, row, columnSpan, rowSpan, width, height, align, justify)
        children.add(cell)
        div.appendChild(cell.view.getElement())
        cell.notifyChanged()
        return cell
    }

    actual fun addAbsolute(
        view: Widget,
        top: Double?,
        right: Double?,
        bottom: Double?,
        left: Double?,
        width: Double?,
        height: Double?
    ): Absolute {
        view.parentImpl = this
        val absolute = Absolute(this, view, top, right, bottom, left, width, height)
        children.add(absolute)
        div.appendChild(absolute.view.getElement())
        absolute.notifyChanged()
        return absolute
    }

    actual fun remove(widget: Widget) {
        for (child in children) {
            if (child.view == widget) {
                widget.parentImpl = null
                children.remove(child)
                div.removeChild(child.view.getElement())
                break
            }
        }
    }

    actual fun get(index: Int): Position = children[index]

    override fun iterator(): Iterator<Position> {
        return children.iterator()
    }

}