package org.kobjects.komponents.core

import platform.UIKit.UITextView
import platform.UIKit.UIView


actual class KTextView actual constructor(kontext: Kontext) : KView() {

    private val uiTextView = UITextView()

    actual fun setText(text: String) {
        uiTextView.text = text
    }

    override fun getView(): UIView {
        return uiTextView
    }

}