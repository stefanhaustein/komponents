package org.kobjects.komponents.core

import org.w3c.dom.Element
import org.w3c.dom.HTMLButtonElement
import org.w3c.dom.HTMLElement

actual class KButton actual constructor(kontext: Kontext) : KView() {

    val button = kontext.document.createElement("button") as HTMLButtonElement

    override fun getElement(): HTMLElement {
        return button
    }

    actual fun setImage(image: KImage) {
        throw RuntimeException("NYI")
    }

    actual fun setText(text: String) {
        throw RuntimeException("NYI")
    }
}