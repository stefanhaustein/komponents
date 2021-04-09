package org.kobjects.komponents.core

import platform.UIKit.UISwitch


actual class KCheckBox actual constructor(kontext: Kontext, value: Boolean) : KView() {

    var uiSwitch = UISwitch()

    actual var value: Boolean
        get() {
            return uiSwitch.on
        }
        set(value) {
            uiSwitch.on = value
        }

    init {
        this.value = value
    }

    override fun getView() = uiSwitch

}