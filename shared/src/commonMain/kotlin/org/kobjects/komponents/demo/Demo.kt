package org.kobjects.komponents.demo

import org.kobjects.komponents.core.KView
import org.kobjects.komponents.core.Kontext


abstract class Demo(
    val kontext: Kontext) {
    abstract val view: KView
    open val animation: (() -> Unit)? = null
}
