package org.kobjects.komponents.core

expect class Slider(
    context: Context,
    value: Int = 0,
    changeListener: ((Slider) -> Unit)? = null
) : AbstractInput<Int, Slider> {
}