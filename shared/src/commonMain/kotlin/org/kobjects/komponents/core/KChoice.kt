package org.kobjects.komponents.core

expect class KChoice(kontext: Kontext) : KView {

    fun setData(data: List<String>)

    fun addSelectionListener(listener: (KChoice, Int, String) -> Unit)
}