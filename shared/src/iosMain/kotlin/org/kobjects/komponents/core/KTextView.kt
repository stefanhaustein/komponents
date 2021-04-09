package org.kobjects.komponents.core

import platform.UIKit.NSLineBreakByWordWrapping
import platform.UIKit.UILabel
import platform.UIKit.UILineBreakModeWordWrap
import platform.UIKit.UIView


actual class KTextView actual constructor(
    kontext: Kontext,
    text: String
) : KView() {
    private val uiLabel = UILabel().also {
        it.numberOfLines = 0
        it.lineBreakMode = UILineBreakModeWordWrap
        it.text = text
    }

    actual var text: String
        get() = uiLabel.text.toString()
        set(value) {
            uiLabel.text = value
        }

    override fun getView(): UIView {
        return uiLabel
    }

}