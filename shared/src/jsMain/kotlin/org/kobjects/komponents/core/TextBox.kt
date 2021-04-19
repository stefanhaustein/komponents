package org.kobjects.komponents.core

import org.w3c.dom.HTMLInputElement

actual class TextBox actual constructor(
    context: Context,
    value: String,
    changeListener: ((TextBox) -> Unit)?
) : AbstractInput<String, TextBox>(changeListener) {

    var input = context.document.createElement("input") as HTMLInputElement

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