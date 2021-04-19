package org.kobjects.komponents.core

import org.w3c.dom.HTMLInputElement

actual class Slider actual constructor(
    context: Context,
    value: Int,
    changeListener: ((Slider) -> Unit)?
) : AbstractInput<Int, Slider>(changeListener) {

    var slider = context.document.createElement("input") as HTMLInputElement

    override var value: Int
        get() {
            return slider.valueAsNumber.toInt()
        }
        set(value) {
            slider.valueAsNumber = value.toDouble()
        }

    override fun getElement() = slider

    init {
        slider.setAttribute("type", "range")
        this.value = value
        slider.addEventListener(
            "input",
            { notifyChangeListeners() })
    }

}