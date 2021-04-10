package org.kobjects.komponents.core

import platform.UIKit.UIAction
import platform.UIKit.UIControlEventValueChanged
import platform.UIKit.UISlider


actual class KSlider actual constructor(
    kontext: Kontext,
    value: Int,
    changeListener: ((KSlider) -> Unit)?
) : AbstractInputView<Int, KSlider>(changeListener) {

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