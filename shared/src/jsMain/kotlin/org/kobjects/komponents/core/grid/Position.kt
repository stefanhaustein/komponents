package org.kobjects.komponents.core.grid

import org.kobjects.komponents.core.KView

actual open class Position actual constructor(
    actual val gridLayout: KGridLayout,
    actual val view: KView
) {
    actual fun notifyChanged() {
        gridLayout.notifyPositionChanged(this)
    }
}