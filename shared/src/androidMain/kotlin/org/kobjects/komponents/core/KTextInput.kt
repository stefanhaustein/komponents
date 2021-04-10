package org.kobjects.komponents.core

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

actual class KTextInput actual constructor(
    kontext: Kontext,
    value: String,
    changeListener: ((KTextInput) -> Unit)?
) : AbstractInputView<String, KTextInput>(changeListener) {

    var editText = EditText(kontext.context)

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