package org.kobjects.komponents.core

expect class KTextView(kontext: Kontext, text: String = "") : KView {

    fun setText(text: String)

}