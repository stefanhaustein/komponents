package org.kobjects.komponents.core

class Positioned(
    val component: Komponent,
    var column: Int = 0,
    var row: Int = 0,
    var columnSpan: Int = 1,
    var rowSpan: Int = 1,
    var width: Double? = null,
    var height: Double? = null,
    var verticalAlign: Align = Align.STRETCH,
    var horizontalAlign: Align = Align.STRETCH) {

    var xPositionCache = 0.0
    var yPositionCache = 0.0
    var measuredWidthCache = 0.0
    var measuredHeightCache = 0.0



}