package org.kobjects.komponents.core

import org.w3c.dom.HTMLInputElement


actual class CheckBox actual constructor(
    context: Context,
    value: Boolean,
    changeListener: ((CheckBox) -> Unit)?
) : AbstractInput<Boolean, CheckBox>(changeListener) {

    var checkBox = context.document.createElement("input") as HTMLInputElement

    override var value: Boolean
        get() {
            return checkBox.checked
        }
        set(value) {
            checkBox.checked = value
        }

    init {
        checkBox.setAttribute("type", "checkbox")
        this.value = value
        checkBox.addEventListener("change", { notifyChangeListeners() })
    }

    override fun getElement() = checkBox

}