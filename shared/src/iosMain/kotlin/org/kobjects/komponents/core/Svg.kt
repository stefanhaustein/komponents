package org.kobjects.komponents.core

import platform.darwin.NSObject


actual class Svg(
    val source: String,
    val svgImage: NSObject
) {
    actual companion object {
        actual fun createSvg(context: Context, code: String): Svg {
            try {
                return Svg(code, context.svgHelper.createSvgImage(code)!!)
            } catch (e: NullPointerException) {
                throw IllegalArgumentException("Failed to parse svg $code", e)
            }
        }
    }


}