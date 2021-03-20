package org.kobjects.komponents.core

import platform.UIKit.UIImageView
import platform.UIKit.UIView

actual class KImageView actual constructor(kontext: Kontext) : KView() {

    val uiImageView = UIImageView()

    actual fun setImage(image: KImage) {
        throw RuntimeException("NYI")
    }

    override fun getView(): UIView {
        return uiImageView
    }
}