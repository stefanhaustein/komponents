package org.kobjects.komponents.core.grid

import org.kobjects.komponents.core.KView

abstract class Position(
    val gridLayout: KGridLayout,
    val view: KView,
    width: Double? = null,
    height: Double? = null
) {
    var width = width
        set(value) {
            field = value
            notifyChanged()
        }
    var height = height
        set(value) {
            field = value
            notifyChanged()
        }


    fun notifyChanged() {
        gridLayout.notifyPositionChanged(this)
    }
}