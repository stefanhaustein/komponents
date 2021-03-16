package org.kobjects.komponents.core

import android.view.View
import org.kobjects.komponents.mobile.ChildLayout

actual class Container actual constructor(kontext: Kontext) : KView() {

    actual var columnGap = 0.0
    actual var rowGap = 0.0

    private val layout = GridLayout(kontext.context, this)
    private val columnWidths = mutableListOf<Size?>()
    private val rowHeights = mutableListOf<Size?>()

    val children = mutableListOf<ChildLayout>()


    override fun getView(): View {
       return layout
    }

    actual fun add(positioned: Positioned) {
        val childLayout = layout.LayoutParams(positioned);
        children.add(childLayout)
        layout.addView(positioned.component.getView(), childLayout)
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