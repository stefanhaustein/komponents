package org.kobjects.komponents.core

import android.view.View
import android.widget.TextView

actual class KTextView actual constructor(kontext: Kontext, text: String) : KView() {

    val textView = TextView(kontext.context).also {
        it.text = text
    }

    override fun getView(): View {
        return textView
    }

    actual fun setText(text: String) {
        textView.text = text
    }

}