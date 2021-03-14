package org.kobjects.komponents.core

expect class SvgKomponent(kontext: Kontext) : Komponent {

    fun setContent(svg: String)
}