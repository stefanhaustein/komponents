package org.kobjects.komponents.core

import org.w3c.dom.Element
import org.w3c.dom.HTMLButtonElement
import org.w3c.dom.HTMLElement
import org.w3c.dom.svg.SVGElement
import org.w3c.dom.svg.SVGSVGElement

actual class KButton actual constructor(
    var kontext: Kontext,
    label: String,
    listener: ((KButton) -> Unit)?
) : KView() {
    private val button = kontext.document.createElement("button") as HTMLButtonElement
    private val clickListeners = mutableListOf<(KButton) -> Unit>()

    actual var label: String = ""
        set(value) {
            field = value
            updateContent()
        }

    actual var image: KImage? = null
        set(value) {
            field = value
            updateContent()
        }

    actual var textAlignment = TextAlignment.CENTER
        set(value) {
            field = value
            button.style.textAlign = value.toString().toLowerCase()
        }

    init {
        this.label = label
        button.addEventListener("click", { clickListeners.forEach {it(this)} })
        textAlignment = TextAlignment.CENTER
    }

    override fun getElement(): HTMLElement {
        return button
    }

    actual fun addClickListener(listener: (KButton) -> Unit) {
        clickListeners.add(listener)
    }

    actual fun removeClickListener(listener: (KButton) -> Unit) {
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
            button.appendChild(kontext.document.createTextNode(label))
        } else {
            button.style.lineHeight = ""
        }
    }
}