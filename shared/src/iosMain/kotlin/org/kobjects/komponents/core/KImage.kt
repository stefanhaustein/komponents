package org.kobjects.komponents.core

import cocoapods.SwiftSVG.*
// import cocoapods.SVGKit.*

actual class KImage(
 //   val svgkImage: SVGKImage
) {


    actual companion object {
        actual fun createSvg(code: String): KImage {
            //val source = SVGKSourceString.sourceFromContentsOfString(code)
            //val img = SVGKImage.imageWithSource(source)
            return KImage(
            //    img!!
            )
        }
    }


}