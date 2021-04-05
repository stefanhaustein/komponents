package org.kobjects.komponents.core

expect class KChoice(
    kontext: Kontext,
    options: List<String> = listOf(),
    selectionListener: ((KChoice) -> Unit)? = null
) : KView {
    var selectedIndex: Int
    var options: List<String>

    fun addSelectionListener(selectionListener: (KChoice) -> Unit)
    fun removeSelectionListener(selectionListener: (KChoice) -> Unit)
}