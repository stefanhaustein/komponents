package org.kobjects.komponents.core

class Action(
    val title: String,
    val handler: (Action) -> Unit) {
}