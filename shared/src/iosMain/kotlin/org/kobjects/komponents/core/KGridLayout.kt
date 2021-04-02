package org.kobjects.komponents.core

import platform.UIKit.UIView
import platform.UIKit.addSubview

actual class KGridLayout actual constructor(kontext: Kontext) : KView() {
    actual var columnGap: Double = 0.0
    actual var rowGap: Double = 0.0
    actual var defaultColumnWidth: Size = Size.AUTO
    actual var defaultRowHeight: Size = Size.AUTO
    actual var paddingLeft: Double = 0.0
    actual var paddingTop: Double = 0.0
    actual var paddingRight: Double = 0.0
    actual var paddingBottom: Double = 0.0
    actual var verticalAlign: Align = Align.START
    actual var horizontalAlign: Align = Align.START

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
        return if (index >= columnWidths.size) defaultColumnWidth else columnWidths[index] ?: defaultColumnWidth
    }

    actual fun getRowHeight(index: Int): Size {
        return if (index >= rowHeights.size) defaultRowHeight else rowHeights[index] ?: defaultRowHeight
    }


    actual fun add(positioned: GridArea) {
        children.add(IosChildLayout(positioned))
        uiGridView.addSubview(positioned.view.getView())
    }

    override fun getView(): UIView {
        return uiGridView
    }
}