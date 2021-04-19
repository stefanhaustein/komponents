package org.kobjects.komponents.core

expect class SvgWidget(context: Context, image: Svg? = null) : Widget {
    var image: Svg?
}