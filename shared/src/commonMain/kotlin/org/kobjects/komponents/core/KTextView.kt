package org.kobjects.komponents.core

expect class KTextView(kontext: Kontext, text: String = "") : KView {

    actual var text: String
}