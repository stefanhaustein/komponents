package org.kobjects.komponents.core

import android.widget.CheckBox

actual class KCheckBox actual constructor(kontext: Kontext, value: Boolean) : KView() {

    var checkBox = CheckBox(kontext.context)

    actual var value: Boolean
        get() {
            return checkBox.isChecked
        }
        set(value) {
            checkBox.isChecked = value
        }

    init {
        this.value = value
    }

    override fun getView() = checkBox

}