package org.kobjects.komponents.core

import platform.UIKit.UIView
import platform.darwin.NSObject

actual class SvgWidget actual constructor(
    val context: Context,
    image: Svg?
) : Widget() {

    private val uiImageView = context.svgHelper.createView(getSvgImage(image))

    actual var image: Svg? = null
        set(value) {
            field = value
            context.svgHelper.updateView(uiImageView, getSvgImage(image))

        }

    override fun getView(): UIView {
        return uiImageView
    }

    private fun getSvgImage(image: Svg?): NSObject {
        return image?.svgImage ?: context.svgHelper.createSvgImage(
            """<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 0 0"></svg>""")!!
    }
}