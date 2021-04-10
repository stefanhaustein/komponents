package org.kobjects.komponents.core

expect class KCheckBox(
    kontext: Kontext,
    value: Boolean = false,
    changeListener: ((KCheckBox) -> Unit)? = null
) : AbstractInputView<Boolean, KCheckBox> {
}