package org.kobjects.komponents.core

expect class KImageView(kontext: Kontext) : KView {

    fun setContent(svg: String)
}