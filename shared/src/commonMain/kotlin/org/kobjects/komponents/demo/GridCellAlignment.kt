package org.kobjects.komponents.demo

import org.kobjects.komponents.core.*
import org.kobjects.komponents.core.grid.Align
import org.kobjects.komponents.core.grid.Cell
import org.kobjects.komponents.core.grid.GridLayout
import org.kobjects.komponents.core.grid.Size


class GridCellAlignment(context: Context) : Demo(context) {

    override val view: Widget

    private fun <T> addChoice(
        container: GridLayout,
        label: String,
        values: Array<T>,
        action1: (T) -> Unit,
        action2: (T) -> Unit
    ) {
        container.addCell(Label(context, label), align = Align.CENTER)

        container.addCell(DropdownList(
            context,
            values.map{if (it is List<*>) it.joinToString(" ") else it.toString().toLowerCase()}) {
            action1(values[it.selectedIndex])
        })

        container.addCell(DropdownList(
            context,
            values.map{if (it is List<*>) it.joinToString(" ") else it.toString().toLowerCase()}) {
            action2(values[it.selectedIndex])
        })

        action1(values[0])
        action2(values[0])
    }

    init {
        val outer = GridLayout(context)

        outer.columnTemplate = listOf(Size.auto(), Size.fr(1.0), Size.fr(1.0))
        outer.rowTemplate = listOf(
            Size.auto(), Size.auto(), Size.auto(),
            Size.auto(), Size.auto(),
            Size.fr(1.0))
        outer.alignContent = Align.STRETCH

        val grid = GridLayout(context)

        val templates = arrayOf(
            listOf(Size.px(80.0), Size.px(80.0), Size.px(80.0)),
            listOf(Size.px(100.0), Size.px(100.0), Size.px(100.0)),
            listOf(Size.fr(1.0), Size.fr(1.0), Size.fr(1.0)),
            listOf(Size.auto(), Size.fr(2.0), Size.fr(3.0)))

        outer.addCell(Label(context))
        outer.addCell(Label(context, "columns"), justify = Align.CENTER)
        outer.addCell(Label(context, "rows"), justify = Align.CENTER)

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
            { grid.forEach{el -> (el as Cell).width = if (it == "implicit") null else 80.0 } },
            { grid.forEach{el -> (el as Cell).height = if (it == "implicit") null else 80.0 } })

        grid.rowGap = 1.0
        grid.columnGap = 1.0

        for (i in 1..9) {
            var textView = Label(context, "$i")
            textView.setBackgroundColor(0xffeeeeeeU)
            grid.addCell(
                textView,
                width = 80.0,
                height = 80.0)
        }

        outer.addCell(
            grid,
            columnSpan = 3,
            align = Align.STRETCH,
            justify = Align.STRETCH)

        view = outer
    }
}

