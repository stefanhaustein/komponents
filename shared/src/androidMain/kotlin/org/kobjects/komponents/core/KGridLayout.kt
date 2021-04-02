package org.kobjects.komponents.core

import android.view.View
import org.kobjects.komponents.core.mobile.ChildLayout

actual class KGridLayout actual constructor(kontext: Kontext) : KView() {

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

    private val layout = GridLayout(kontext.context, this)
    private val columnWidths = mutableListOf<Size?>()
    private val rowHeights = mutableListOf<Size?>()

    val children = mutableListOf<ChildLayout>()


    override fun getView(): View {
       return layout
    }

    actual fun add(positioned: GridArea) {
        positioned.gridLayout = this
        val childLayout = layout.LayoutParams(positioned);
        children.add(childLayout)
        layout.addView(positioned.view.getView(), childLayout)
    }

    actual var autoColumns: Size = Size.AUTO

    actual var autoRows: Size = Size.AUTO

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
        return if (index >= columnWidths.size) autoColumns else columnWidths[index] ?: autoColumns
    }

    actual fun getRowHeight(index: Int): Size {
        return if (index >= rowHeights.size) autoRows else rowHeights[index] ?: autoRows
    }

    actual fun notifyAreaChanged(area: GridArea) {
        layout.requestLayout()
    }

    actual fun templateColumnCount(): Int {
        return columnWidths.size
    }

    actual fun templateRowCount(): Int {
        return rowHeights.size
    }


}