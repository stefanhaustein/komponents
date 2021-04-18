package org.kobjects.komponents.core.grid

import android.view.View
import org.kobjects.komponents.core.KView
import org.kobjects.komponents.core.Kontext
import org.kobjects.komponents.core.grid.mobile.ResolvedPosition

actual class KGridLayout actual constructor(kontext: Kontext) : KView(), Iterable<Position> {

    actual var columnGap = 0.0
        set(value) {
            field = value; layout.requestLayout()
        }
    actual var rowGap = 0.0
        set(value) {
            field = value; layout.requestLayout()
        }
    actual var paddingLeft = 0.0
        set(value) {
            field = value; layout.requestLayout()
        }
    actual var paddingTop = 0.0
        set(value) {
            field = value; layout.requestLayout()
        }
    actual var paddingBottom = 0.0
        set(value) {
            field = value; layout.requestLayout()
        }
    actual var paddingRight = 0.0
        set(value) {
            field = value; layout.requestLayout()
        }
    actual var justifyContent = Align.START
        set(value) {
            field = value; layout.requestLayout()
        }
    actual var alignContent = Align.START
        set(value) {
            field = value; layout.requestLayout()
        }
    actual var justifyItems = Align.STRETCH
        set(value) {
            field = value; layout.requestLayout()
        }
    actual var alignItems = Align.STRETCH
        set(value) {
            field = value; layout.requestLayout()
        }
    actual var columnTemplate = listOf<Size>()
        set(value) {
            field = value; layout.requestLayout()
        }
    actual var rowTemplate = listOf<Size>()
        set(value) {
            field = value; layout.requestLayout()
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

    val layout = GridLayout(kontext.context, this)

    val children = mutableListOf<Position>()

    override fun getView(): View {
        return layout
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
        justify: Align?
    ): Cell {
        val cell = Cell(this, view, column, row, columnSpan, rowSpan, width, height, align, justify)
        children.add(cell)
        layout.addView(cell.view.getView(), layout.LayoutParams(cell))
        return cell
    }

    actual fun addAbsolute(
        view: KView,
        top: Double?,
        right: Double?,
        bottom: Double?,
        left: Double?,
        width: Double?,
        height: Double?
    ): Absolute {
        val absolute = Absolute(this, view, top, right, bottom, left, width, height)
        children.add(absolute)
        layout.addView(view.getView(), layout.LayoutParams(absolute))
        return absolute
    }



    actual var autoColumns: Size = Size.AUTO

    actual var autoRows: Size = Size.AUTO

    fun notifyPositionChanged() {
        layout.requestLayout()
    }

    actual fun get(index: Int): Position {
        return children[index]
    }

    override fun iterator(): Iterator<Position> {
        return children.iterator()
    }
}