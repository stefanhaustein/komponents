package org.kobjects.komponents.core

expect class TextBox (
    context: Context,
    value: String = "",
    changeListener: ((TextBox) -> Unit)? = null
) : AbstractInput<String, TextBox> {
}