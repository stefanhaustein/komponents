package org.kobjects.komponents.demo

import org.kobjects.komponents.core.*

class Demo(
    val kontext: Kontext,
    val display: (KView) -> Unit) {

    fun showMainMenu() {
        val layout = KGridLayout(kontext)

        val button = KButton(kontext)
        button.setText("Grid Cell Alignment")
        button.addClickListener{ showGridCellAlignment() }

        layout.add(GridArea(button))

        display(layout)
    }

    fun showGridCellAlignment() {
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
        display(grid)
    }
}