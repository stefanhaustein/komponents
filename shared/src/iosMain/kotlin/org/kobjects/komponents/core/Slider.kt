package org.kobjects.komponents.core

import platform.UIKit.UIAction
import platform.UIKit.UIControlEventValueChanged
import platform.UIKit.UISlider


actual class Slider actual constructor(
    context: Context,
    value: Int,
    changeListener: ((Slider) -> Unit)?
) : AbstractInput<Int, Slider>(changeListener) {

    var slider = UISlider()

    override var value: Int
        get() {
            return slider.value.toInt()
        }
        set(value) {
            slider.setValue(value.toFloat())
        }

    override fun getView() = slider

    init {
        slider.maximumValue = 100.0f
        slider.addAction(
            UIAction.actionWithHandler { notifyChangeListeners() },
            forControlEvents = UIControlEventValueChanged
        )
    }

}