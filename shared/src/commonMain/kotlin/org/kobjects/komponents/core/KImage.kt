package org.kobjects.komponents.core

expect class KImage {
    companion object {
        fun createSvg(kontext: Kontext, code: String): KImage
    }

}