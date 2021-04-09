package org.kobjects.komponents.core

expect class KCheckBox(kontext: Kontext, value: Boolean = false) : KView {
    var value: Boolean
}