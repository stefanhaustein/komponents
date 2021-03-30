package org.kobjects.komponents.core

import platform.UIKit.UIImageView
import platform.UIKit.UIView

actual class KImageView actual constructor(val kontext: Kontext) : KView() {

    val uiImageView = UISvgView(kontext.svgHelper)


    actual fun setImage(image: KImage) {
        kontext.svgHelper.createLayer(image.svg) {uiImageView.setSvgLayer(it)}
    }

    override fun getView(): UIView {
        return uiImageView
    }
}