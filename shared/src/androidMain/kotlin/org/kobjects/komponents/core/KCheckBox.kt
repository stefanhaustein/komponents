package org.kobjects.komponents.core

import android.widget.CheckBox
import android.widget.CompoundButton

actual class KCheckBox actual constructor(
    kontext: Kontext,
    value: Boolean,
    changeListener: ((KCheckBox) -> Unit)?
) : AbstractInputView<Boolean, KCheckBox>(changeListener) {

    var checkBox = CheckBox(kontext.context)

    override var value: Boolean
        get() {
            return checkBox.isChecked
        }
        set(value) {
            checkBox.isChecked = value
        }

    init {
        this.value = value
        checkBox.setOnCheckedChangeListener { button, value ->
            notifyChangeListeners()
        }
    }

    override fun getView() = checkBox
}