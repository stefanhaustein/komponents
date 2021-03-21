package org.kobjects.komponents.core


actual class KImage() {

    actual companion object {
        actual fun createSvg(code: String): KImage {
            throw RuntimeException("NYI")
        }
    }


}