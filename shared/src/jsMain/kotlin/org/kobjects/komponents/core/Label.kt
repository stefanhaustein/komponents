package org.kobjects.komponents.core


import org.w3c.dom.HTMLElement

actual class Label actual constructor(context: Context, text: String) : Widget() {

    val div = (context.document.createElement("div") as HTMLElement).also {
        it.innerHTML = htmlify(text)
    }

    actual var text: String
        get() = div.textContent ?: ""
        set(value) {
            div.innerHTML = htmlify(value)
        }

    init {
        this.text = text
    }


    override fun getElement(): HTMLElement {
        return div
    }


    companion object {
        fun htmlify(text: String): String {
            val sb = StringBuilder()
            for (i in 0 until text.length) {
                val c = text[i]
                when (c) {
                    '\n' -> sb.append("<br>")
                    '<' -> sb.append("&lt;")
                    '"' -> sb.append("&quot;")
                    '&' -> sb.append("&amp;")
                    '>' -> sb.append("&gt;")
                    else -> sb.append(c)
                }
            }
            return sb.toString()
        }
    }

}