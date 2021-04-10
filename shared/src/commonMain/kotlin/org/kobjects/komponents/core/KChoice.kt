package org.kobjects.komponents.core


expect class KChoice<T>(
    kontext: Kontext,
    options: List<T> = listOf<T>(),
    stringify: (T) -> String = { it.toString() },
    changeListener: ((KChoice<T>) -> Unit)? = null
) : AbstractInputView<T, KChoice<T>> {
    var selectedIndex: Int
    var options: List<T>
}

