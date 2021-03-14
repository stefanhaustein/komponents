package org.kobjects.komponents.core

import android.view.View
import android.widget.TextView

actual class TextKomponent actual constructor(kontext: Kontext) : Komponent() {

    val textView = TextView(kontext.context)

    override fun getView(): View {
        return textView
    }

    actual fun setText(text: String) {
        textView.text = text
    }

}