package org.kobjects.komponents.core

import platform.UIKit.UIImageView

actual class KImage() {


    actual companion object {
        actual fun createSvg(code: String): KImage {
            throw RuntimeException("NYI")
        }
    }


}