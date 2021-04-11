package org.kobjects.komponents.core

import android.util.TypedValue
import android.view.View
import androidx.appcompat.widget.AppCompatTextView

actual class KTextView actual constructor(kontext: Kontext, text: String) : KView() {

    private val textView = AppCompatTextView(kontext.context)

    actual var text: String
        get() = textView.text.toString()
        set(value) {
            textView.text = value
        }

    override fun getView(): View {
        return textView
    }

    init {
        textView.text = text
    }
}