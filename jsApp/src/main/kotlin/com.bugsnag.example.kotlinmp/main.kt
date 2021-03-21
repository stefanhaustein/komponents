package com.bugsnag.example.kotlinmp

import kotlinx.browser.document
import org.kobjects.komponents.core.Kontext
import org.kobjects.komponents.demo.Demo


fun main() {
    document.addEventListener("DOMContentLoaded", {
        val gridElement = Demo().alignDemo(Kontext(document)).getElement()
        gridElement.style.height = "100%"
        document.body!!.appendChild(gridElement)
    })
}
