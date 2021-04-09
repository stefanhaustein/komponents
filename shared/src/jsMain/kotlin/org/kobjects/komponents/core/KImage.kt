package org.kobjects.komponents.core


actual class KImage(val svg: String) {



    actual companion object {
        actual fun createSvg(kontext: Kontext, code: String): KImage {
            return KImage(code)
        }
    }


}