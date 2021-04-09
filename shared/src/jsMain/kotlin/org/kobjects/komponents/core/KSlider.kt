package org.kobjects.komponents.core

import org.w3c.dom.HTMLInputElement

actual class KSlider actual constructor(kontext: Kontext, value: Double) : KView() {

    var slider = kontext.document.createElement("input") as HTMLInputElement

    actual var value: Double
        get() {
            return slider.valueAsNumber
        }
        set(value) {
            slider.valueAsNumber = value
        }

    init {
        slider.setAttribute("type", "range")
        this.value = value
    }

    override fun getElement() = slider
}