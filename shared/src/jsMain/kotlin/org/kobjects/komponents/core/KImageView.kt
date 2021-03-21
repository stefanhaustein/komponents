package org.kobjects.komponents.core

import org.w3c.dom.HTMLElement
import org.w3c.dom.HTMLImageElement

actual class KImageView actual constructor(kontext: Kontext) : KView() {

    private val element = kontext.document.createElement("img") as HTMLImageElement

    override fun getElement(): HTMLElement {
        return element
    }

    actual fun setImage(image: KImage) {
        throw RuntimeException("NYI")
    }
}