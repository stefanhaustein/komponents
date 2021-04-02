package org.kobjects.komponents.core

import kotlinx.browser.window
import org.w3c.dom.HTMLElement
import org.w3c.dom.HTMLSelectElement
import org.w3c.dom.get

actual class KChoice actual constructor(val kontext: Kontext) :
    KView() {

    var selectElement = kontext.document.createElement("select") as HTMLSelectElement
    var listeners = mutableListOf<(KChoice, Int, String) -> Unit>()

    init {
        selectElement.addEventListener("change", {
            console.log("Event received!")
            val selectedIndex = selectElement.selectedIndex
            val selectedText = selectElement.get(selectedIndex)?.textContent ?: ""
            for (listener in listeners) {
                listener(this@KChoice, selectedIndex, selectedText)
            }
        })
    }

    actual fun setData(data: List<String>) {
        selectElement.innerHTML = ""
        for (item in data) {
            val optionElement = kontext.document.createElement("option")
            optionElement.textContent = item
            selectElement.appendChild(optionElement)
        }
    }

    actual fun addSelectionListener(listener: (KChoice, Int, String) -> Unit) {
        listeners.add(listener)
    }

    override fun getElement(): HTMLElement {
        return selectElement
    }

    actual fun setSelectedIndex(index: Int) {
        selectElement.selectedIndex = index
    }

}