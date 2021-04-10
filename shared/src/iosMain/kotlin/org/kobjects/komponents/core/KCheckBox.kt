package org.kobjects.komponents.core

import platform.UIKit.UIAction
import platform.UIKit.UIControlEventValueChanged
import platform.UIKit.UISwitch


actual class KCheckBox actual constructor(
    kontext: Kontext,
    value: Boolean,
    changeListener: ((KCheckBox) -> Unit)?
) : AbstractInputView<Boolean, KCheckBox>(changeListener) {

    var uiSwitch = UISwitch()

    override var value: Boolean
        get() {
            return uiSwitch.on
        }
        set(value) {
            uiSwitch.on = value
        }

    init {
        this.value = value
        uiSwitch.addAction(UIAction.actionWithHandler { notifyChangeListeners() },
            forControlEvents = UIControlEventValueChanged)

    }

    override fun getView() = uiSwitch

}