package org.kobjects.komponents.core

import android.view.View
import android.widget.TextView

actual class KTextView actual constructor(kontext: Kontext, text: String) : KView() {

    private val textView = TextView(kontext.context)

    actual var text: String
        get() = textView.text.toString()
        set(value) {
            textView.text = value
        }

    override fun getView(): View {
        return textView
    }

    init {
        this.text = text
    }
}