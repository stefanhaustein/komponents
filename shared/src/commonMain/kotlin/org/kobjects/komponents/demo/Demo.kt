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
        layout.add(GridArea(image, width = 100.0, height = 100.0, justify = Align.CENTER))


        val button = KButton(kontext)
        button.setText("Grid Cell Alignment")
        button.addClickListener{
            select(Selector.GRID_CELL_ALIGNMENT, renderDemo(Selector.GRID_CELL_ALIGNMENT))
        }

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

    private fun <T> addChoice(
        container: KGridLayout,
        label: String,
        values: Array<T>,
        action1: (T) -> Unit,
        action2: (T) -> Unit
    ) {
        val labelView = KTextView(kontext)
        labelView.setText(label)
        container.add(GridArea(labelView, align = Align.CENTER))

        val choice1 = KChoice(kontext)
        choice1.setData(values.map{if (it is List<*>) it.joinToString(" ") else it.toString()})
        choice1.addSelectionListener { choice1, index, label ->
            action1(values[index])
        }
        container.add(GridArea(choice1))

        val choice2 = KChoice(kontext)
        choice2.setData(values.map{if (it is List<*>) it.joinToString(" ") else it.toString()})
        choice2.addSelectionListener { choice2, index, label ->
            action2(values[index])
        }
        container.add(GridArea(choice2))

        action1(values[0])
        action2(values[0])
    }

    fun renderGridCellAlignment(): KGridLayout {
        val outer = KGridLayout(kontext)

        outer.columnTemplate = listOf(Size.auto(), Size.fr(1.0), Size.fr(1.0))
        outer.rowTemplate = listOf(Size.auto(), Size.auto(), Size.auto(),Size.auto(), Size.fr(1.0))
        outer.alignContent = Align.STRETCH

        val grid = KGridLayout(kontext)

        val templates = arrayOf(
            listOf(Size.px(80.0), Size.px(80.0), Size.px(80.0)),
            listOf(Size.px(100.0), Size.px(100.0), Size.px(100.0)),
            listOf(Size.fr(1.0), Size.fr(1.0), Size.fr(1.0)),
            listOf(Size.auto(), Size.fr(2.0), Size.fr(3.0)))

        outer.add(GridArea(KTextView(kontext)))
        outer.add(GridArea(KTextView(kontext, "columns"), justify = Align.CENTER))
        outer.add(GridArea(KTextView(kontext, "rows"), justify = Align.CENTER))

        addChoice(outer, "content pos.", Align.values(),
            { grid.justifyContent = it },
            { grid.alignContent = it })
        addChoice(outer, "item pos.", Align.values(),
            { grid.justifyItems = it },
            { grid.alignItems = it })
        addChoice(outer, "template", templates,
            { grid.columnTemplate = it },
            { grid.rowTemplate = it })

        grid.rowGap = 1.0
        grid.columnGap = 1.0

        for (i in 1..9) {
           var textView = KTextView(kontext)
           textView.setText("$i")
           textView.setBackgroundColor(0xffeeeeeeU)
           grid.add(GridArea(
               textView,
               width = 80.0,
               height = 80.0))
        }

        outer.add(GridArea(grid,
            columnSpan = 3,
            align = Align.STRETCH,
            justify = Align.STRETCH))
        return outer
    }



    enum class Selector(val title: String) {
        GRID_CELL_ALIGNMENT("Grid Cell Alignment")
    }

}