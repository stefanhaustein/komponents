package org.kobjects.komponents.core

expect class KImageView(kontext: Kontext, image: KImage? = null) : KView {
    var image: KImage?
}