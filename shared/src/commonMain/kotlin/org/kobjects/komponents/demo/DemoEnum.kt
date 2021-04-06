package org.kobjects.komponents.demo

import org.kobjects.komponents.core.Kontext

enum class DemoEnum(val title: String, val render: (Kontext) -> Demo) {
    GRID_CELL_ALIGNMENT("Grid Cell Alignment", { GridCellAlignment(it) }),
    WIDGET_GALLERY("Widget Gallery", { WidgetGallery(it) }),
    ANIMATION("Animation", {AnimationDemo(it) })
}