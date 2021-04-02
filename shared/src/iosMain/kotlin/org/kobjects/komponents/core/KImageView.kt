package org.kobjects.komponents.core

import platform.QuartzCore.CALayer
import platform.UIKit.UIView
import cocoapods.SwiftSVG.*
import platform.Foundation.*

actual class KImageView actual constructor(val kontext: Kontext) : KView() {

    val uiImageView = IosSvgView(kontext.svgHelper)


    actual fun setImage(image: KImage) {
        /*
        val str = image.svg as NSString
        val data = str.dataUsingEncoding(NSUTF8StringEncoding)
        CALayer.Companion.create(SVGData = data, parser = null) {
        }
        */
        kontext.svgHelper.createLayer(image.svg) {uiImageView.setSvgLayer(it)}
    }

    override fun getView(): UIView {
        return uiImageView
    }
}