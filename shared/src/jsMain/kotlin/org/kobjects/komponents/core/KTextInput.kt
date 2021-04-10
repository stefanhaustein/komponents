package org.kobjects.komponents.core

import org.w3c.dom.HTMLInputElement

actual class KTextInput actual constructor(
    kontext: Kontext,
    value: String,
    changeListener: ((KTextInput) -> Unit)?
) : AbstractInputView<String, KTextInput>(changeListener) {

    var input = kontext.document.createElement("input") as HTMLInputElement

    override var value: String
        get() {
            return input.value
        }
        set(value) {
            input.value = value
        }

    override fun getElement() = input
    
    init {
        input.setAttribute("type", "text")
        this.value = value
        input.addEventListener("input", { notifyChangeListeners() })
    }

}