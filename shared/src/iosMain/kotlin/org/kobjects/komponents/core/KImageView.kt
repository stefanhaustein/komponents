package org.kobjects.komponents.core

import platform.UIKit.UIImageView
import platform.UIKit.UIView

actual class KImageView actual constructor(val kontext: Kontext) : KView() {

    val uiImageView = UIView()


    actual fun setImage(image: KImage) {
        kontext.svgHandler(image.svg, uiImageView)
    }

    override fun getView(): UIView {
        return uiImageView
    }
}