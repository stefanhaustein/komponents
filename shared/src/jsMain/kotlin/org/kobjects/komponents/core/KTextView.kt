package org.kobjects.komponents.core


import org.w3c.dom.HTMLElement

actual class KTextView actual constructor(kontext: Kontext) : KView() {

    val div = kontext.document.createElement("div") as HTMLElement

    override fun getElement(): HTMLElement {
        return div
    }

    actual fun setText(text: String) {
        div.textContent = text
    }

}