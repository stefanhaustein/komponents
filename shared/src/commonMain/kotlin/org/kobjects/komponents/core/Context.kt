package org.kobjects.komponents.core

expect class Context {

    fun alert(
        title: String,
        okAction: Action,
        cancelAction: Action? = null)

    fun requestAnimationFrame(callback: () -> Unit)
}