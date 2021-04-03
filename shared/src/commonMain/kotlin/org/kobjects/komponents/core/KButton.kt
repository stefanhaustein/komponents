package org.kobjects.komponents.core

expect class KButton(kontext: Kontext, label: String = "") : KView {

    fun setImage(image: KImage)

    fun setText(text: String)

    fun addClickListener(listener: (KButton) -> Unit)
}