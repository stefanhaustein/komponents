package org.kobjects.komponents.core

import org.w3c.dom.HTMLInputElement

actual class KSlider actual constructor(
    kontext: Kontext,
    value: Int,
    changeListener: ((KSlider) -> Unit)?
) : AbstractInputView<Int, KSlider>(changeListener) {

    var slider = kontext.document.createElement("input") as HTMLInputElement

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