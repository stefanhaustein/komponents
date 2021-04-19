package org.kobjects.komponents.demo

import org.kobjects.komponents.core.Context

enum class DemoEnum(val title: String, val render: (Context) -> Demo) {
    GRID_CELL_ALIGNMENT("Grid Cell Alignment", { GridCellAlignment(it) }),
    WIDGET_GALLERY("Widget Gallery", { WidgetGallery(it) }),
    DRAG_GESTURE("Drag Gesture", {CheckersDemo(it) })
}