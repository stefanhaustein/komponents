package org.kobjects.komponents.core

expect class KButton(
    kontext: Kontext,
    label: String = "",
    listener: ((KButton) -> Unit)? = null) : KView {

    var label: String
    var image: KImage?

    fun addClickListener(listener: (KButton) -> Unit)

    fun removeClickListener(listener: (KButton) -> Unit)
}