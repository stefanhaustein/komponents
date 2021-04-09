package org.kobjects.komponents.core

import platform.darwin.NSObject


actual class KImage(
    val source: String,
    val svgImage: NSObject
) {
    actual companion object {
        actual fun createSvg(kontext: Kontext, code: String): KImage {
            try {
                return KImage(code, kontext.svgHelper.createSvgImage(code)!!)
            } catch (e: NullPointerException) {
                throw IllegalArgumentException("Failed to parse svg $code", e)
            }
        }
    }


}