package org.kobjects.komponents.demo

import org.kobjects.komponents.core.*


class GridCellAlignment(kontext: Kontext) : Demo(kontext) {

    override val view: KView

    private fun <T> addChoice(
        container: KGridLayout,
        label: String,
        values: Array<T>,
        action1: (T) -> Unit,
        action2: (T) -> Unit
    ) {
        container.add(Cell(KTextView(kontext, label), align = Align.CENTER))

        container.add(
            Cell(KChoice(
            kontext,
            values.map{if (it is List<*>) it.joinToString(" ") else it.toString().toLowerCase()}) {
            action1(values[it.selectedIndex])
        })
        )

        container.add(
            Cell(KChoice(
            kontext,
            values.map{if (it is List<*>) it.joinToString(" ") else it.toString().toLowerCase()}) {
            action2(values[it.selectedIndex])
        })
        )

        action1(values[0])
        action2(values[0])
    }

    init {
        val outer = KGridLayout(kontext)

        outer.columnTemplate = listOf(Size.auto(), Size.fr(1.0), Size.fr(1.0))
        outer.rowTemplate = listOf(
            Size.auto(), Size.auto(), Size.auto(),
            Size.auto(), Size.auto(),
            Size.fr(1.0))
        outer.alignContent = Align.STRETCH

        val grid = KGridLayout(kontext)

        val templates = arrayOf(
            listOf(Size.px(80.0), Size.px(80.0), Size.px(80.0)),
            listOf(Size.px(100.0), Size.px(100.0), Size.px(100.0)),
            listOf(Size.fr(1.0), Size.fr(1.0), Size.fr(1.0)),
            listOf(Size.auto(), Size.fr(2.0), Size.fr(3.0)))

        outer.add(Cell(KTextView(kontext)))
        outer.add(Cell(KTextView(kontext, "columns"), justify = Align.CENTER))
        outer.add(Cell(KTextView(kontext, "rows"), justify = Align.CENTER))

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
            grid.add(
                Cell(
                textView,
                width = 80.0,
                height = 80.0)
            )
        }

        outer.add(
            Cell(grid,
            columnSpan = 3,
            align = Align.STRETCH,
            justify = Align.STRETCH)
        )

        view = outer
    }
}

