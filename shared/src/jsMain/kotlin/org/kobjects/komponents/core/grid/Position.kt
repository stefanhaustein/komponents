package org.kobjects.komponents.core.grid

import org.kobjects.komponents.core.Widget

actual open class Position actual constructor(
    actual val gridLayout: GridLayout,
    actual val view: Widget
) {
    fun px(value: Double?) = if (value == null) "" else "${value}px"

    actual fun notifyChanged() {
        val style = view.getElement().style
        when (this) {
            is Cell -> {
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
            is Absolute -> {
                style.position = "absolute"
                style.top = px(this.top)
                style.right = px(this.right)
                style.bottom = px(this.bottom)
                style.left = px(this.left)
                style.width = px(this.width)
                style.height = px(this.height)
            }
        }
    }
}