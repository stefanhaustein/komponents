package com.bugsnag.example.kotlinmp

import kotlinx.browser.document
import org.kobjects.komponents.core.Kontext
import org.kobjects.komponents.demo.Demo


fun main() {
    document.addEventListener("DOMContentLoaded", {
        Demo(Kontext(document)) {
            val element = it.getElement()
            element.style.height = "100%"
            document.body!!.innerHTML = ""
            document.body!!.appendChild(element)
        }.showMainMenu()
    })
}
