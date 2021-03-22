package org.kobjects.komponents.core

actual class Positioned actual constructor(
    component: KView,
    column: Int,
    row: Int,
    columnSpan: Int,
    rowSpan: Int,
    width: Double?,
    height: Double?,
    verticalAlign: Align,
    horizontalAlign: Align
) {
    actual val component: KView = component
    actual var column: Int = column
    set(value) {
        field = value
        component.getElement().style.setProperty(
            "grid-column", "${column + 1} / ${column + 1 + columnSpan}")
    }
    actual var columnSpan: Int = columnSpan
        set(value) {
            field = value
            component.getElement().style.setProperty(
                "grid-column", "${column + 1} / ${column + 1 + columnSpan}")
        }
    actual var row: Int = column
        set(value) {
            field = value
            component.getElement().style.setProperty(
                "grid-row", "${row + 1} / ${row + rowSpan + 1}")
        }
    actual var rowSpan: Int = rowSpan
        set(value) {
            field = value
            component.getElement().style.setProperty(
                "grid-row", "${row + 1} / ${row + rowSpan + 1}")
        }
    actual var width: Double? = width
    actual var height: Double? = height
    actual var verticalAlign: Align = verticalAlign
        set(value) {
            field = value
            component.getElement().style.alignSelf = value.toString()
        }
    actual var horizontalAlign: Align = horizontalAlign
        set(value) {
            field = value
            component.getElement().style.setProperty("justify-self", value.toString())
        }

    init {
        this.row = row
        this.column = column
        this.verticalAlign = verticalAlign
        this.horizontalAlign = horizontalAlign
    }

}