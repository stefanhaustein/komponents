package org.kobjects.komponents.core

actual class GridArea actual constructor(
    view: KView,
    column: Int?,
    row: Int?,
    columnSpan: Int,
    rowSpan: Int,
    width: Double?,
    height: Double?,
    verticalAlign: Align,
    horizontalAlign: Align
) {
    actual val view: KView = view
    actual var column: Int? = column
        set(value) {
            field = value
            view.getElement().style.setProperty(
                "grid-column-start",
                if (value != null) "${value + 1}" else "")
        }
    actual var columnSpan: Int = columnSpan
        set(value) {
            field = value
            view.getElement().style.setProperty(
                "grid-column-end",
              "span $value")
        }
    actual var row: Int? = row
        set(value) {
            field = value
            view.getElement().style.setProperty(
                "grid-row-start",
                if (value != null) "${value + 1}" else "")
        }
    actual var rowSpan: Int = rowSpan
        set(value) {
            field = value
            view.getElement().style.setProperty(
                "grid-row-end",
                "span $value" )
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
            updatePlace()
        }
    actual var horizontalAlign: Align = horizontalAlign
        set(value) {
            field = value
            updatePlace()
        }


    fun updatePlace() {
        view.getElement().style.setProperty(
            "place-self",
            "${verticalAlign.name.toLowerCase()} ${horizontalAlign.name.toLowerCase()}"
        )
    }

    init {
        this.row = row
        this.column = column
        this.columnSpan = columnSpan
        this.rowSpan = rowSpan
        updatePlace()
        if (width != null) {
            this.width = width
        }
        if (height != null) {
            this.height = width
        }
    }

}