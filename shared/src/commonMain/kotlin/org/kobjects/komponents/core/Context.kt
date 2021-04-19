package org.kobjects.komponents.core

expect class Context {

    fun requestAnimationFrame(callback: () -> Unit)


}