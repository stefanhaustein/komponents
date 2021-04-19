package org.kobjects.komponents.core

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

actual class TextBox actual constructor(
    context: Context,
    value: String,
    changeListener: ((TextBox) -> Unit)?
) : AbstractInput<String, TextBox>(changeListener) {

    var editText = EditText(context.context)

    override var value: String
        get() {
            return editText.text.toString()
        }
        set(value) {
            editText.setText(value)
        }


    override fun getView() = editText


    init {
        this.value = value
        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
            override fun afterTextChanged(s: Editable?) {
                notifyChangeListeners()
            }
        })
    }
}