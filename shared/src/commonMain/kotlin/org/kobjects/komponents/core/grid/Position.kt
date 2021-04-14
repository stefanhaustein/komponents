package org.kobjects.komponents.core.grid

import org.kobjects.komponents.core.KView

expect open class Position(
    gridLayout: KGridLayout,
    view: KView
) {
    val gridLayout: KGridLayout
    val view: KView

    fun notifyChanged()
}