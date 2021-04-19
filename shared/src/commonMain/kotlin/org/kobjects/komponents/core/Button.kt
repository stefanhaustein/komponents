package org.kobjects.komponents.core

expect class Button(
    context: Context,
    label: String = "",
    listener: ((Button) -> Unit)? = null) : Widget {

    var label: String
    var image: Svg?
    var textAlignment: TextAlignment

    fun addClickListener(listener: (Button) -> Unit)

    fun removeClickListener(listener: (Button) -> Unit)
}