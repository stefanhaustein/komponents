package org.kobjects.komponents.demo

import org.kobjects.komponents.core.*

class Demo(
    val kontext: Kontext,
    val select: (Selector, KGridLayout) -> Unit) {


    fun renderMenu(): KGridLayout {
        val layout = KGridLayout(kontext)

        val button = KButton(kontext)
        button.setText("Grid Cell Alignment")
        button.addClickListener{ select(Selector.GRID_CELL_ALIGNMENT, renderDemo(Selector.GRID_CELL_ALIGNMENT)) }
    //    button.setBackgroundColor(0xffffff00u)

        layout.defaultColumnWidth = Size.fr(1.0)

        layout.add(GridArea(button))
        layout.horizontalAlign = Align.CENTER
        //   layout.setBackgroundColor(0xffff0000u)

        layout.paddingBottom = 24.0
        layout.paddingTop = 24.0

        layout.paddingRight = 12.0
        layout.paddingLeft = 12.0

        return layout
    }

    fun renderDemo(selector: Selector): KGridLayout {
        return when(selector) {
            Selector.GRID_CELL_ALIGNMENT -> renderGridCellAlignment()
        }
    }

    fun renderGridCellAlignment(): KGridLayout {
        val grid = KGridLayout(kontext)

        grid.setColumnWidth(0, Size.fr(1.0), repeat = Align.values().size)
        grid.setRowHeight(0, Size.fr(1.0), repeat = Align.values().size)

        grid.rowGap = 4.0
        grid.columnGap = 4.0

        for (vAlign in Align.values()) {
            for (hAlign in Align.values()) {
                var tc = KTextView(kontext)
                tc.setText("v: $vAlign\nh: $hAlign")
                tc.setBackgroundColor(0xffeeeeeeU)

                grid.add(
                    GridArea(
                        tc,
                        row = hAlign.ordinal,
                        column = vAlign.ordinal,
                        horizontalAlign = hAlign,
                        verticalAlign = vAlign
                    )
                )
            }
        }
        return grid
    }


    enum class Selector(val title: String) {
        GRID_CELL_ALIGNMENT("Grid Cell Alignment")
    }

}