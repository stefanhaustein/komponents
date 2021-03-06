package org.kobjects.komponents.core

import org.w3c.dom.HTMLButtonElement
import org.w3c.dom.HTMLElement
import org.w3c.dom.svg.SVGElement
import org.w3c.dom.svg.SVGSVGElement

actual class Button actual constructor(
    var context: Context,
    label: String,
    listener: ((Button) -> Unit)?
) : Widget() {
    private val button = context.document.createElement("button") as HTMLButtonElement
    private val clickListeners = mutableListOf<(Button) -> Unit>()

    actual var label: String = ""
        set(value) {
            field = value
            updateContent()
        }

    actual var image: Svg? = null
        set(value) {
            field = value
            updateContent()
        }

    actual var textAlignment = TextAlignment.CENTER
        set(value) {
            field = value
            button.style.textAlign = value.toString().toLowerCase()
        }


    override fun getElement(): HTMLElement {
        return button
    }

    actual fun addClickListener(listener: (Button) -> Unit) {
        clickListeners.add(listener)
    }

    actual fun removeClickListener(listener: (Button) -> Unit) {
        clickListeners.remove(listener)
    }

    fun updateContent() {
        button.textContent = label
        if (image != null) {
            button.innerHTML = image!!.svg
//            (button.firstChild as SVGElement).style.display = "block"

            val htmlElement = (button.firstChild as SVGElement)
            val svgElement = (button.firstChild as SVGSVGElement)
            htmlElement.style.setProperty("float", "left")
            val height = svgElement.viewBox.baseVal.height
            val width = svgElement.viewBox.baseVal.width
            htmlElement.style.height = "${height}px"
            htmlElement.style.width = "${width}px"
            button.style.lineHeight = "${height}px"
            button.appendChild(context.document.createTextNode(label))
        } else {
            button.style.lineHeight = ""
        }
    }


    init {
        this.label = label
        button.addEventListener("click", { clickListeners.forEach {it(this)} })
        textAlignment = TextAlignment.CENTER
        if (listener != null) {
            addClickListener(listener)
        }
    }
}