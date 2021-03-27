package org.kobjects.komponents.demo.js

import kotlinx.browser.document
import kotlinx.browser.window
import org.kobjects.komponents.core.KView
import org.kobjects.komponents.core.Kontext
import org.kobjects.komponents.demo.Demo


fun main() {
    fun show(title: String, kView: KView) {
        document.title = title
        val element = kView.getElement()
        element.style.height = "100%"
        document.body!!.innerHTML = ""
        document.body!!.appendChild(element)
    }

    val demo = Demo(Kontext(document)) { selected, kView ->
        window.location.hash = "#" + selected.name
        show(selected.title, kView)
    }

    fun showMenu() {
        show("Komponents Demo", demo.renderMenu())
    }

    fun resolveHash() {
        if (window.location.hash.isEmpty()) {
            showMenu()
        } else {
            try {
                val selector = Demo.Selector.valueOf(window.location.hash.substring(1))
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
