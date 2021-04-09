package org.kobjects.komponents.core

import platform.UIKit.UISlider


actual class KSlider actual constructor(kontext: Kontext, value: Double) : KView() {

    var slider = UISlider()

    actual var value: Double
        get() {
            return slider.value.toDouble()
        }
        set(value) {
            slider.setValue(value.toFloat())
        }

    override fun getView() = slider
}