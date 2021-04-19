package org.kobjects.komponents.core

import android.view.View
import androidx.appcompat.widget.AppCompatTextView

actual class Label actual constructor(context: Context, text: String) : Widget() {

    private val textView = AppCompatTextView(context.context)

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