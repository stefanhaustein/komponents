package org.kobjects.komponents.core

import org.w3c.dom.HTMLElement
import org.w3c.dom.HTMLSelectElement

actual class KChoice<T> actual constructor(
    val kontext: Kontext,
    options: List<T>,
    val stringify: (T) -> String,
    changeListener: ((KChoice<T>) -> Unit)?
) : AbstractInputView<T, KChoice<T>>(changeListener) {

    private var selectElement = kontext.document.createElement("select") as HTMLSelectElement

    override var value: T
        get() {
            return options[selectedIndex]
        }
        set(value) {
            selectedIndex = options.indexOf(value)
        }

    actual var options: List<T> = listOf()
        set(value) {
            field = value
            selectElement.innerHTML = ""
            for (item in options) {
                val optionElement = kontext.document.createElement("option")
                optionElement.textContent = stringify(item)
                selectElement.appendChild(optionElement)
            }
        }

    actual var selectedIndex: Int
        get() = selectElement.selectedIndex
        set(value) {
            selectElement.selectedIndex = value
        }

    override fun getElement(): HTMLElement {
        return selectElement
    }

    init {
        this.options = options
        selectElement.addEventListener(
            "change",
            { notifyChangeListeners() })
    }
}