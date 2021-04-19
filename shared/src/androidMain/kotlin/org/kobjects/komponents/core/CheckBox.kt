package org.kobjects.komponents.core

import android.widget.CheckBox

actual class CheckBox actual constructor(
    context: Context,
    value: Boolean,
    changeListener: ((org.kobjects.komponents.core.CheckBox) -> Unit)?
) : AbstractInput<Boolean, org.kobjects.komponents.core.CheckBox>(changeListener) {

    var checkBox = CheckBox(context.context)

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