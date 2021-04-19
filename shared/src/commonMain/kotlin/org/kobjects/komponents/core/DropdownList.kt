package org.kobjects.komponents.core


expect class DropdownList<T>(
    context: Context,
    options: List<T> = listOf<T>(),
    stringify: (T) -> String = { it.toString() },
    changeListener: ((DropdownList<T>) -> Unit)? = null
) : AbstractInput<T, DropdownList<T>> {
    var selectedIndex: Int
    var options: List<T>
}

