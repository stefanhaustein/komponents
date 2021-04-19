package org.kobjects.komponents.core

expect class CheckBox(
    context: Context,
    value: Boolean = false,
    changeListener: ((CheckBox) -> Unit)? = null
) : AbstractInput<Boolean, CheckBox> {
}