package org.kobjects.komponents.core

expect class KButton(kontext: Kontext) : KView {

    fun setImage(image: KImage)

    fun setText(text: String)
}