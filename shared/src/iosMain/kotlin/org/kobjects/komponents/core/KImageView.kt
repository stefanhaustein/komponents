package org.kobjects.komponents.core

import platform.UIKit.UIView
import platform.darwin.NSObject

actual class KImageView actual constructor(
    val kontext: Kontext,
    image: KImage?
) : KView() {

    private val uiImageView = kontext.svgHelper.createView(getSvgImage(image))

    actual var image: KImage? = null
        set(value) {
            field = value
            kontext.svgHelper.updateView(uiImageView, getSvgImage(image))

        }

    override fun getView(): UIView {
        return uiImageView
    }

    private fun getSvgImage(image: KImage?): NSObject {
        return image?.svgImage ?: kontext.svgHelper.createSvgImage(
            """<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 0 0"></svg>""")!!
    }
}