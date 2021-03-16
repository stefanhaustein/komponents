package org.kobjects.komponents.core

import android.view.View
import android.widget.TextView

actual class KTextView actual constructor(kontext: Kontext) : KView() {

    val textView = TextView(kontext.context)

    override fun getView(): View {
        return textView
    }

    actual fun setText(text: String) {
        textView.text = text
    }

}