package org.kobjects.komponents.core

import org.w3c.dom.HTMLInputElement


actual class KCheckBox actual constructor(
    kontext: Kontext,
    value: Boolean,
    changeListener: ((KCheckBox) -> Unit)?
) : AbstractInputView<Boolean, KCheckBox>(changeListener) {

    var checkBox = kontext.document.createElement("input") as HTMLInputElement

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