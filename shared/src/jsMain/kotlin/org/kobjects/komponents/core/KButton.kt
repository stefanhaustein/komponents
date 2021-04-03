package org.kobjects.komponents.core

import org.w3c.dom.Element
import org.w3c.dom.HTMLButtonElement
import org.w3c.dom.HTMLElement

actual class KButton actual constructor(kontext: Kontext, label: String) : KView() {

    val button = (kontext.document.createElement("button") as HTMLButtonElement).also {
        it.textContent = label
    }

    override fun getElement(): HTMLElement {
        return button
    }

    actual fun setImage(image: KImage) {
        throw RuntimeException("NYI")
    }

    actual fun setText(text: String) {
        button.textContent = text
    }

    actual fun addClickListener(listener: (KButton) -> Unit) {
        button.addEventListener("click", {listener(this)})
    }
}