package org.kobjects.komponents.core.grid

import org.kobjects.komponents.core.Widget

expect open class Position(
    gridLayout: GridLayout,
    view: Widget
) {
    val gridLayout: GridLayout
    val view: Widget

    fun notifyChanged()
}