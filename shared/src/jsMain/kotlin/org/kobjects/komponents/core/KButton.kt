package org.kobjects.komponents.core

import org.w3c.dom.Element
import org.w3c.dom.HTMLButtonElement
import org.w3c.dom.HTMLElement

actual class KButton actual constructor(
    kontext: Kontext,
    label: String,
    listener: ((KButton) -> Unit)?
) : KView() {
    private val button = kontext.document.createElement("button") as HTMLButtonElement
    private val clickListeners = mutableListOf<(KButton) -> Unit>()

    actual var label: String
        get() = button.textContent ?: ""
        set(value) {
            button.textContent = value
        }

    actual var image: KImage? = null

    init {
        this.label = label
        button.addEventListener("click", { clickListeners.forEach {it(this)} })
    }

    override fun getElement(): HTMLElement {
        return button
    }

    actual fun addClickListener(listener: (KButton) -> Unit) {
        clickListeners.add(listener)
    }

    actual fun removeClickListener(listener: (KButton) -> Unit) {
        clickListeners.remove(listener)
    }
}