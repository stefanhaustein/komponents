package org.kobjects.komponents.core

expect class KImage {
    companion object {
        fun createSvg(code: String): KImage
    }

}