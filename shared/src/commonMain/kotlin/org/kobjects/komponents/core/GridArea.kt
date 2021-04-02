package org.kobjects.komponents.core

class GridArea (
    val view: KView,
    column: Int? = null,
    row: Int? = null,
    columnSpan: Int = 1,
    rowSpan: Int = 1,
    width: Double? = null,
    height: Double? = null,
    verticalAlign: Align? = null,
    horizontalAlign: Align? = null) {

    var column = column
        set(value) {
            field = value
            notifyChanged()
        }

    var columnSpan = columnSpan
        set(value) {
            field = value
            notifyChanged()
        }

    var row = row
        set(value) {
            field = value
            notifyChanged()
        }
    var rowSpan = rowSpan
        set(value) {
            field = value
            notifyChanged()
        }
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
    var verticalAlign = verticalAlign
        set(value) {
            field = value
            notifyChanged()
        }
    var horizontalAlign = horizontalAlign
        set(value) {
            field = value
            notifyChanged()
        }

    var gridLayout: KGridLayout? = null

    fun notifyChanged() {
        gridLayout?.notifyAreaChanged(this)
    }

}