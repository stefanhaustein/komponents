package org.kobjects.komponents.core

import platform.UIKit.UIImage
import platform.UIKit.UIView
import platform.darwin.NSObject

interface SvgHelper {
    fun createView(svgImage: NSObject): UIView
    fun updateView(view: UIView, svgImage: NSObject)

    fun createSvgImage(xml: String): NSObject?

    fun getUIImage(svgImage: NSObject): UIImage
}