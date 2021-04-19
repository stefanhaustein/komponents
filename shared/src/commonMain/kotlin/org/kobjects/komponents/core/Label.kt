package org.kobjects.komponents.core

expect class Label(context: Context, text: String = "") : Widget {

    actual var text: String
}