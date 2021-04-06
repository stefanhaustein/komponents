package org.kobjects.komponents.core

expect abstract class KView {

    val transformation: Transformation

    fun setBackgroundColor(color: UInt)

}