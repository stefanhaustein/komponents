package org.kobjects.komponents.core.grid

import org.kobjects.komponents.core.KView

actual open class Position actual constructor(
    actual val gridLayout: KGridLayout,
    actual val view: KView
) {
    actual fun notifyChanged() {
        when (this) {
            is Cell -> {
                val style = view.getElement().style
                val column = this.column
                val row = this.row
                style.setProperty(
                    "grid-column-start",
                    if (column != null) "${column + 1}" else "")
                style.setProperty(
                    "grid-column-end",
                    "span ${columnSpan}")
                style.setProperty(
                    "grid-row-start",
                    if (row != null) "${row + 1}" else "")
                style.setProperty(
                    "grid-row-end",
                    "span ${rowSpan}" )
                style.width = if (width != null) "${width}px" else ""
                style.height = if (height != null) "${width}px" else ""
                style.setProperty(
                    "align-self",
                    verticalAlign?.name?.toLowerCase() ?: ""
                )
                style.setProperty(
                    "justify-self",
                    horizontalAlign?.name?.toLowerCase() ?: ""
                )
            }
        }


    }
}