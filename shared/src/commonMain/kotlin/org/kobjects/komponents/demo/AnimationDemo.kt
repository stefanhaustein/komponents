package org.kobjects.komponents.demo

import org.kobjects.komponents.core.*

class AnimationDemo(kontext: Kontext) : Demo(kontext) {

    override val view: KView
    override val animation: (() -> Unit)?

    init {
        val grid = KGridLayout(kontext)
        grid.alignContent = Align.CENTER
        grid.justifyContent = Align.CENTER
        grid.alignItems = Align.CENTER
        grid.justifyItems = Align.CENTER
        grid.columnTemplate = listOf(Size.auto())
        grid.rowTemplate = listOf(Size.auto())

        val checkerboard = KGridLayout(kontext)
        checkerboard.columnTemplate = List(8) { Size.px(40.0) }
        checkerboard.rowTemplate = List(8) { Size.px(40.0) }

        for (y in 0 until 8) {
            for (x in 0 until 8) {
                checkerboard.add(GridArea(KTextView(kontext).also {
                    it.setBackgroundColor(
                        if ((x + y) and 1 == 0) 0xffccccccu else 0xff444444u) }))

            }
        }

        grid.add(GridArea(
            checkerboard,
            column = 0,
            row = 0,
            width = 8 * 40.0,   // Why -- shouldn't the template be sufficient?
            height = 8 * 40.0))

        view = grid

        animation = {
            checkerboard.transformation.rotation += 0.2
        }
    }

}