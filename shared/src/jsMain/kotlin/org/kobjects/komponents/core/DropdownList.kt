package org.kobjects.komponents.core

import org.w3c.dom.HTMLElement
import org.w3c.dom.HTMLSelectElement

actual class DropdownList<T> actual constructor(
    val context: Context,
    options: List<T>,
    val stringify: (T) -> String,
    changeListener: ((DropdownList<T>) -> Unit)?
) : AbstractInput<T, DropdownList<T>>(changeListener) {

    private var selectElement = context.document.createElement("select") as HTMLSelectElement

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
                val optionElement = context.document.createElement("option")
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