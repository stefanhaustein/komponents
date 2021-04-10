package org.kobjects.komponents.core

expect class KTextInput (
    kontext: Kontext,
    value: String = "",
    changeListener: ((KTextInput) -> Unit)? = null
) : AbstractInputView<String, KTextInput> {
}