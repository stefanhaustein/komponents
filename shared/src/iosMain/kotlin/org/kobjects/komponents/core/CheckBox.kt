package org.kobjects.komponents.core

import platform.UIKit.UIAction
import platform.UIKit.UIControlEventValueChanged
import platform.UIKit.UISwitch


actual class CheckBox actual constructor(
    context: Context,
    value: Boolean,
    changeListener: ((CheckBox) -> Unit)?
) : AbstractInput<Boolean, CheckBox>(changeListener) {

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