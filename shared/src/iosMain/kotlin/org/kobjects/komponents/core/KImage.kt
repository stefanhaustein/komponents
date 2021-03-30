package org.kobjects.komponents.core

import cocoapods.SwiftSVG.*
import platform.Foundation.NSData
import platform.UIKit.UIView

// import cocoapods.SVGKit.*

actual class KImage(
    val svg: String
) {


    actual companion object {
        actual fun createSvg(code: String): KImage {
            //val source = SVGKSourceString.sourceFromContentsOfString(code)
            //val img = SVGKImage.imageWithSource(source)
            return KImage(code)
        }
    }


}