package org.kobjects.komponents.core

expect class KSlider(
    kontext: Kontext,
    value: Int = 0,
    changeListener: ((KSlider) -> Unit)? = null
) : AbstractInputView<Int, KSlider> {
}