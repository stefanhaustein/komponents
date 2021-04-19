package org.kobjects.komponents.core


actual class Svg(val svg: String) {



    actual companion object {
        actual fun createSvg(context: Context, code: String): Svg {
            return Svg(code)
        }
    }


}