package org.kobjects.komponents.core.grid

import android.view.View
import org.kobjects.komponents.core.KView
import org.kobjects.komponents.core.Kontext
import org.kobjects.komponents.core.grid.mobile.ResolvedPosition

actual class KGridLayout actual constructor(kontext: Kontext) : KView(), Iterable<Cell> {

    actual var columnGap = 0.0
        set(value) { field = value; layout.requestLayout() }
    actual var rowGap = 0.0
        set(value) { field = value; layout.requestLayout() }
    actual var paddingLeft = 0.0
        set(value) { field = value; layout.requestLayout() }
    actual var paddingTop = 0.0
        set(value) { field = value; layout.requestLayout() }
    actual var paddingBottom = 0.0
        set(value) { field = value; layout.requestLayout() }
    actual var paddingRight = 0.0
        set(value) { field = value; layout.requestLayout() }
    actual var justifyContent = Align.START
        set(value) { field = value; layout.requestLayout() }
    actual var alignContent = Align.START
        set(value) { field = value; layout.requestLayout() }
    actual var justifyItems = Align.STRETCH
        set(value) { field = value; layout.requestLayout() }
    actual var alignItems = Align.STRETCH
        set(value) { field = value; layout.requestLayout() }
    actual var columnTemplate = listOf<Size>()
        set(value) { field = value; layout.requestLayout() }
    actual var rowTemplate = listOf<Size>()
        set(value) { field = value; layout.requestLayout() }
    actual val cellCount: Int
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

    private val layout = GridLayout(kontext.context, this)

    val children = mutableListOf<ResolvedPosition>()


    override fun getView(): View {
       return layout
    }

    actual fun addCell(positioned: Cell) {
        positioned.gridLayout = this
        val childLayout = layout.LayoutParams(positioned);
        children.add(childLayout)
        layout.addView(positioned.view.getView(), childLayout)
    }

    actual var autoColumns: Size = Size.AUTO

    actual var autoRows: Size = Size.AUTO

    actual fun notifyPositionChanged(position: Position) {
        layout.requestLayout()
    }

    actual fun getCell(index: Int): Cell {
        return children[index].positioned
    }

    override fun iterator(): Iterator<Cell> {
        return children.map{it.positioned}.iterator()
    }

}