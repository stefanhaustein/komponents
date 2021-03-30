package org.kobjects.komponents.demo

import org.kobjects.komponents.core.*

class Demo(
    val kontext: Kontext,
    val select: (Selector, KGridLayout) -> Unit) {


    fun renderMenu(): KGridLayout {
        val layout = KGridLayout(kontext)

        val image = KImageView(kontext)
        image.setBackgroundColor(0x88888888u)
        image.setImage(KImage.createSvg("""
            <svg xmlns="http://www.w3.org/2000/svg" version="1.2" baseProfile="tiny" width="30" height="30" 
                 viewBox="0 0 30 30">
              <rect x="10" y="10" width="10" height="10" fill="#ff0000"/>
            </svg>
        """.trimIndent()))
        layout.add(GridArea(image, width = 100.0, height = 100.0, horizontalAlign = Align.CENTER))


        val button = KButton(kontext)
        button.setText("Grid Cell Alignment")
        button.addClickListener{ select(Selector.GRID_CELL_ALIGNMENT, renderDemo(Selector.GRID_CELL_ALIGNMENT)) }
    //    button.setBackgroundColor(0xffffff00u)

        layout.defaultColumnWidth = Size.fr(1.0)

        layout.add(GridArea(button, row=1))
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