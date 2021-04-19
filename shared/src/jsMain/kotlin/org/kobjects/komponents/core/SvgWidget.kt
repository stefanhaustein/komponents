package org.kobjects.komponents.core

import org.w3c.dom.HTMLDivElement
import org.w3c.dom.HTMLElement
import org.w3c.dom.svg.SVGElement

actual class SvgWidget actual constructor(
    context: Context,
    image: Svg?
) : Widget() {

    private val element = context.document.createElement("div") as HTMLDivElement

    actual var image: Svg? = null
        set(value) {
            field = value
            if (value == null) {
                element.innerHTML = ""
            } else {
                element.innerHTML = value.svg
                try {
                    val svgElement = element.firstElementChild as SVGElement
                    svgElement.style.width = "100%"
                    svgElement.style.height = "100%"
                } catch (e: Exception) {
//              System.out.println(element.f)
                }
            }
        }

    init {
        this.image = image
    }

    override fun getElement(): HTMLElement {
        return element
    }


}