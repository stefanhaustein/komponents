package org.kobjects.komponents.core

import kotlinx.browser.window
import org.w3c.dom.HTMLElement
import org.w3c.dom.HTMLSelectElement
import org.w3c.dom.get

actual class KChoice actual constructor(
    val kontext: Kontext,
    options: List<String>,
    selectionListener: ((KChoice) -> Unit)?
) : KView() {

    private var selectElement = kontext.document.createElement("select") as HTMLSelectElement
    private var listeners = mutableListOf<(KChoice) -> Unit>()

    actual var options: List<String> = listOf<String>()
        set(value) {
            field = value
            selectElement.innerHTML = ""
            for (item in options) {
                val optionElement = kontext.document.createElement("option")
                optionElement.textContent = item
                selectElement.appendChild(optionElement)
            }
        }

    actual var selectedIndex: Int
        get() = selectElement.selectedIndex
        set(value) {
            selectElement.selectedIndex = value
        }

    init {
        if (options != null) {
            this.options = options
        }
        selectElement.addEventListener(
            "change",
            { listeners.forEach{ it(this@KChoice) } })
        if (selectionListener != null) {
            addSelectionListener(selectionListener)
        }
    }

    actual fun addSelectionListener(selectionListener: (KChoice) -> Unit) {
        listeners.add(selectionListener)
    }

    override fun getElement(): HTMLElement {
        return selectElement
    }

    actual fun removeSelectionListener(selectionListener: (KChoice) -> Unit) {
        listeners.remove(selectionListener)
    }

}