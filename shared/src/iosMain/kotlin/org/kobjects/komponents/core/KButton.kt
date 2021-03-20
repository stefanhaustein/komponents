package org.kobjects.komponents.core

import platform.UIKit.UIButton
import platform.UIKit.UIControlStateNormal
import platform.UIKit.UIView

actual class KButton actual constructor(kontext: Kontext) : KView() {

    val uiButton = UIButton()

    actual fun setImage(image: KImage) {
        // tbd
    }

    actual fun setText(text: String) {
        uiButton.setTitle(text, UIControlStateNormal)
    }

    override fun getView(): UIView {
       return uiButton
    }

}