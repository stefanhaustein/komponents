package org.kobjects.komponents.core

import org.w3c.dom.HTMLInputElement


actual class KCheckBox actual constructor(kontext: Kontext, value: Boolean) : KView() {

    var checkBox = kontext.document.createElement("input") as HTMLInputElement

    actual var value: Boolean
        get() {
            return checkBox.checked
        }
        set(value) {
            checkBox.checked = value
        }

    init {
        checkBox.setAttribute("type", "checkbox")
        this.value = value
    }

    override fun getElement() = checkBox

}