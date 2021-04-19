package org.kobjects.komponents.demo.js

import kotlinx.browser.document
import kotlinx.browser.window
import org.kobjects.komponents.core.Widget
import org.kobjects.komponents.core.Context
import org.kobjects.komponents.demo.DemoEnum
import org.kobjects.komponents.demo.DemoMenu


fun main() {
    fun show(title: String, widget: Widget) {
        document.title = title
        val element = widget.getElement()
        element.style.height = "100%"
        document.body!!.innerHTML = ""
        document.body!!.appendChild(element)
    }

    val demo = DemoMenu(Context(document)) { selected, kView ->
        window.location.hash = "#" + selected.name
        show(selected.title, kView)
    }

    fun showMenu() {
        show("Komponents Demo", demo.view)
    }

    fun resolveHash() {
        if (window.location.hash.isEmpty()) {
            showMenu()
        } else {
            try {
                val selector = DemoEnum.valueOf(window.location.hash.substring(1))
                show(selector.title, demo.renderDemo(selector))
            } catch (e: Exception) {
                showMenu()
            }
        }
    }

    document.addEventListener("DOMContentLoaded", {
        window.addEventListener("hashchange", {
            resolveHash()
        }, false);

        resolveHash()
    })
}
