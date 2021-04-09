package org.kobjects.komponents.core

import platform.UIKit.UIView

actual class KImageView actual constructor(
    val kontext: Kontext,
    image: KImage?
) : KView() {

    private val uiImageView = kontext.svgHelper.createView(svgOrEmpty(image))

    actual var image: KImage? = null
        set(value) {
            field = value
            kontext.svgHelper.updateView(uiImageView, svgOrEmpty(image))

        }

    override fun getView(): UIView {
        return uiImageView
    }

    private fun svgOrEmpty(image: KImage?):String {
        return image?.svg ?: """<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 0 0"></svg>"""
    }
}