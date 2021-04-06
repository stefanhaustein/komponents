package org.kobjects.komponents.demo

import org.kobjects.komponents.core.*

class WidgetGallery(kontext: Kontext) : Demo(kontext) {
    override val view: KView

    init {
        val grid = KGridLayout(kontext)
        grid.columnTemplate = listOf(Size.fr(1.0))
        grid.add(GridArea(KChoice(kontext, listOf("KChoice", "Alternative"))))
        grid.add(GridArea(KButton(kontext, "KButton")))
        grid.add(GridArea(KTextView(kontext, "KTextView")))
        view = grid;
    }
}