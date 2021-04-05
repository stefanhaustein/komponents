package org.kobjects.komponents.core

import platform.QuartzCore.CALayer
import platform.UIKit.UIView
import cocoapods.SwiftSVG.*
import platform.Foundation.*

actual class KImageView actual constructor(
    val kontext: Kontext,
    image: KImage?
) : KView() {

    private val uiImageView = IosSvgView(kontext.svgHelper)

    actual var image: KImage? = null
        set(value) {
            field = value
            /*
            val str = image.svg as NSString
            val data = str.dataUsingEncoding(NSUTF8StringEncoding)
            CALayer.Companion.create(SVGData = data, parser = null) {
            }
            */
            if (value != null) {
                kontext.svgHelper.createLayer(value.svg) {
                    uiImageView.setSvgLayer(it)
                }
            }
        }

    override fun getView(): UIView {
        return uiImageView
    }
}