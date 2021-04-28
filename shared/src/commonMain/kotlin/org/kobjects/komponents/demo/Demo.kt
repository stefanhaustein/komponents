package org.kobjects.komponents.demo

import org.kobjects.komponents.core.Widget
import org.kobjects.komponents.core.Context


abstract class Demo(
    val context: Context) {
    abstract val view: Widget
    open val animation: ((Double) -> Unit)? = null
}
