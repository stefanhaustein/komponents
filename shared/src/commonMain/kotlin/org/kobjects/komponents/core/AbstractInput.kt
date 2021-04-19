package org.kobjects.komponents.core


abstract class AbstractInput<T, V>(changeListener: ((V) -> Unit)?) : Widget() {

    abstract var value: T

    var changeListeners = if (changeListener == null) mutableListOf() else mutableListOf(changeListener)

    fun addChangeListener(changeListener: (V) -> Unit) {
        changeListeners.add(changeListener)
    }

    fun notifyChangeListeners() {
        changeListeners.forEach { it(this as V) }
    }

}