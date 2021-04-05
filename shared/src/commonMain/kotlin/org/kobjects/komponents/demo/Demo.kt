package org.kobjects.komponents.demo

import org.kobjects.komponents.core.*

class Demo(
    val kontext: Kontext,
    val select: (Selector, KGridLayout) -> Unit) {

    fun renderMenu(): KGridLayout {
        val layout = KGridLayout(kontext)

        val image = KImageView(kontext)
        image.setBackgroundColor(0x88888888u)
        image.image = KImage.createSvg("""
            <svg viewBox="0 0 30 30">
              <rect x="0" y="0" width="10" height="10" fill="#ff0000"/>
              <rect x="10" y="10" width="10" height="10" fill="#00ff00"/>
              <rect x="20" y="20" width="10" height="10" fill="#0000ff"/>
            </svg>
        """.trimIndent())
        layout.add(GridArea(image, width = 100.0, height = 100.0, justify = Align.CENTER))



        for (demo in Selector.values()) {
            val button = KButton(kontext, demo.title)
            button.addClickListener {
                select(demo, demo.render(this))
            }
            layout.add(GridArea(button))
        }


        layout.autoColumns = Size.fr(1.0)

        layout.justifyContent = Align.CENTER
        //   layout.setBackgroundColor(0xffff0000u)

        layout.paddingBottom = 24.0
        layout.paddingTop = 24.0

        layout.paddingRight = 12.0
        layout.paddingLeft = 12.0

        return layout
    }

    fun renderDemo(selector: Selector): KGridLayout {
        return selector.render(this)
    }

    private fun <T> addChoice(
        container: KGridLayout,
        label: String,
        values: Array<T>,
        action1: (T) -> Unit,
        action2: (T) -> Unit
    ) {
        container.add(GridArea(KTextView(kontext, label), align = Align.CENTER))

        container.add(GridArea(KChoice(
            kontext,
            values.map{if (it is List<*>) it.joinToString(" ") else it.toString().toLowerCase()}) {
                action1(values[it.selectedIndex])
        }))

        container.add(GridArea(KChoice(
            kontext,
            values.map{if (it is List<*>) it.joinToString(" ") else it.toString().toLowerCase()}) {
                action2(values[it.selectedIndex])
        }))

        action1(values[0])
        action2(values[0])
    }

    fun renderGridCellAlignment(): KGridLayout {
        val outer = KGridLayout(kontext)

        outer.columnTemplate = listOf(Size.auto(), Size.fr(1.0), Size.fr(1.0))
        outer.rowTemplate = listOf(Size.auto(), Size.auto(), Size.auto(),Size.auto(), Size.auto(),Size.fr(1.0))
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

        addChoice(outer, "content pos.", arrayOf(Align.CENTER, Align.START, Align.END),
            { grid.justifyContent = it },
            { grid.alignContent = it })
        addChoice(outer, "item pos.", arrayOf(Align.CENTER, Align.START, Align.END, Align.STRETCH),
            { grid.justifyItems = it },
            { grid.alignItems = it })
        addChoice(outer, "template", templates,
            { grid.columnTemplate = it },
            { grid.rowTemplate = it })
        addChoice(outer, "box size", arrayOf("80", "implicit"),
            { grid.forEach{el -> el.width = if (it == "implicit") null else 80.0 } },
            { grid.forEach{el -> el.height = if (it == "implicit") null else 80.0 } })

        grid.rowGap = 1.0
        grid.columnGap = 1.0

        for (i in 1..9) {
           var textView = KTextView(kontext, "$i")
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

    fun renderWidgetGallery(): KGridLayout {
        val grid = KGridLayout(kontext)
        grid.columnTemplate = listOf(Size.fr(1.0))
        grid.add(GridArea(KChoice(kontext, listOf("KChoice", "Alternative"))))
        grid.add(GridArea(KButton(kontext, "KButton")))
        grid.add(GridArea(KTextView(kontext, "KTextView")))
        return grid
    }


    enum class Selector(val title: String, val render: (Demo) -> KGridLayout) {
        GRID_CELL_ALIGNMENT("Grid Cell Alignment", { it.renderGridCellAlignment() }),
        WIDGET_GALLERY("Widget Gallery", { it.renderWidgetGallery() })
    }

}