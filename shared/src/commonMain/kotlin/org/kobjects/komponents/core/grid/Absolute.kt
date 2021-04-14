package org.kobjects.komponents.core.grid

import org.kobjects.komponents.core.KView

class Absolute(
    gridLayout: KGridLayout,
    view: KView,
    top: Double? = null,
    left: Double? = null,
    bottom: Double? = null,
    right: Double? = null,
    width: Double? = null,
    height: Double? = null,
) : Position(gridLayout, view, width, height) {
    var top: Double? = top
        set(value) {
            field = value
            notifyChanged()
        }
    var left: Double? = left
        set(value) {
            field = value
            notifyChanged()
        }
    var bottom: Double? = bottom
        set(value) {
            field = value
            notifyChanged()
        }
    var right: Double? = right
        set(value) {
            field = value
            notifyChanged()
        }
}