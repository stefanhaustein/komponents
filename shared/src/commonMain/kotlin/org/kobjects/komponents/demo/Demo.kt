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
            <svg viewBox="0 0 30 30">
              <rect x="0" y="0" width="10" height="10" fill="#ff0000"/>
              <rect x="10" y="10" width="10" height="10" fill="#00ff00"/>
              <rect x="20" y="20" width="10" height="10" fill="#0000ff"/>
            </svg>
        """.trimIndent()))
        layout.add(GridArea(image, width = 100.0, height = 100.0, horizontalAlign = Align.CENTER))


        val button = KButton(kontext)
        button.setText("Grid Cell Alignment")
        button.addClickListener{ select(Selector.GRID_CELL_ALIGNMENT, renderDemo(Selector.GRID_CELL_ALIGNMENT)) }
    //    button.setBackgroundColor(0xffffff00u)

        val picker = KChoice(kontext)
        picker.setData(listOf("foo", "bar", "baz", "foobar"))
        layout.add(GridArea(picker))

        layout.autoColumns = Size.fr(1.0)

        layout.add(GridArea(button))
        layout.justifyContent = Align.CENTER
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

    private fun addLabeledChoice(
        container: KGridLayout,
        label: String,
        values: Array<Align>,
        action: (Align) -> Unit
    ) {
        val labelView = KTextView(kontext)
        labelView.setText(label)
        container.add(GridArea(labelView))

        val choice = KChoice(kontext)
        choice.setData(values.map{it.toString()})
        choice.addSelectionListener { kChoice, i, s ->
            action(values[i])
        }
        container.add(GridArea(choice))
    }

    fun renderGridCellAlignment(): KGridLayout {
        val outer = KGridLayout(kontext)
        outer.setColumnWidth(0, Size.auto())
        outer.setColumnWidth(1, Size.fr(1.0))
        outer.alignContent = Align.STRETCH
        outer.setRowHeight(2, Size.fr(1.0))

        val grid = KGridLayout(kontext)

        addLabeledChoice(outer, "justify items", Align.values()) { grid.justifyItems = it }
        addLabeledChoice(outer, "align items", Align.values()) { grid.alignItems = it }


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
                        width = 50.0,
                        height = 50.0
       //                 horizontalAlign = hAlign,
       //                 verticalAlign = vAlign
                    )
                )
            }
        }
        outer.add(GridArea(grid, columnSpan = 2))
        return outer
    }



    enum class Selector(val title: String) {
        GRID_CELL_ALIGNMENT("Grid Cell Alignment")
    }

}