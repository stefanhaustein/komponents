package org.kobjects.komponents.core

actual class GridArea actual constructor(
    view: KView,
    column: Int,
    row: Int,
    columnSpan: Int,
    rowSpan: Int,
    width: Double?,
    height: Double?,
    verticalAlign: Align,
    horizontalAlign: Align
) {
    actual val view: KView = view
    actual var column: Int = column
        set(value) {
            field = value
            updateArea()
        }
    actual var columnSpan: Int = columnSpan
        set(value) {
            field = value
            updateArea()
        }
    actual var row: Int = row
        set(value) {
            field = value
            updateArea()
        }
    actual var rowSpan: Int = rowSpan
        set(value) {
            field = value
            updateArea()
        }
    actual var width: Double? = width
        set(value) {
            field = value
            view.getElement().style.width = if (width != null) "${width}px" else ""
        }
    actual var height: Double? = height
    actual var verticalAlign: Align = verticalAlign
        set(value) {
            field = value
            view.getElement().style.width = if (height != null) "${height}px" else ""
        }
    actual var horizontalAlign: Align = horizontalAlign
        set(value) {
            field = value
            updatePlace()
        }

    fun updateArea() {
        view.getElement().style.setProperty(
            "grid-area",
            "${row + 1} / ${column + 1} / ${row + 1 + rowSpan} / ${column + 1 + columnSpan}")
    }

    fun updatePlace() {
        view.getElement().style.setProperty(
            "place-self",
            "${verticalAlign.name.toLowerCase()} ${horizontalAlign.name.toLowerCase()}"
        )
    }

    init {
        updateArea()
        updatePlace()
        if (width != null) {
            this.width = width
        }
        if (height != null) {
            this.height = width
        }
    }

}