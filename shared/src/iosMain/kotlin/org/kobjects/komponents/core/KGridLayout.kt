package org.kobjects.komponents.core

import platform.UIKit.UIView
import platform.UIKit.addSubview
import platform.UIKit.setNeedsLayout

actual class KGridLayout actual constructor(kontext: Kontext) : KView() {
    actual var columnGap: Double = 0.0
        set(value) { field = value; uiGridView.setNeedsLayout() }
    actual var rowGap: Double = 0.0
        set(value) { field = value; uiGridView.setNeedsLayout() }
    actual var autoColumns: Size = Size.AUTO
        set(value) { field = value; uiGridView.setNeedsLayout() }
    actual var autoRows: Size = Size.AUTO
        set(value) { field = value; uiGridView.setNeedsLayout() }
    actual var paddingLeft: Double = 0.0
        set(value) { field = value; uiGridView.setNeedsLayout() }
    actual var paddingTop: Double = 0.0
        set(value) { field = value; uiGridView.setNeedsLayout() }
    actual var paddingRight: Double = 0.0
        set(value) { field = value; uiGridView.setNeedsLayout() }
    actual var paddingBottom: Double = 0.0
        set(value) { field = value; uiGridView.setNeedsLayout() }
    actual var alignContent: Align = Align.START
        set(value) { field = value; uiGridView.setNeedsLayout() }
    actual var justifyContent: Align = Align.START
        set(value) { field = value; uiGridView.setNeedsLayout() }
    actual var alignItems: Align = Align.START
        set(value) { field = value; uiGridView.setNeedsLayout() }
    actual var justifyItems: Align = Align.START
        set(value) { field = value; uiGridView.setNeedsLayout() }

    private val columnWidths = mutableListOf<Size?>()
    private val rowHeights = mutableListOf<Size?>()
    private val uiGridView = IosGridView(this)

    val children = mutableListOf<IosChildLayout>()


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


    actual fun add(positioned: GridArea) {
        positioned.gridLayout = this
        children.add(IosChildLayout(positioned))
        uiGridView.addSubview(positioned.view.getView())
    }

    override fun getView(): UIView {
        return uiGridView
    }

    actual fun notifyAreaChanged(area: GridArea) {
        uiGridView.setNeedsLayout()
    }

    actual fun templateColumnCount(): Int {
        return columnWidths.size
    }

    actual fun templateRowCount(): Int {
        return rowHeights.size
    }
}