package org.kobjects.komponents.core


import org.w3c.dom.HTMLElement

actual class KTextView actual constructor(kontext: Kontext) : KView() {

    val div = kontext.document.createElement("div") as HTMLElement

    override fun getElement(): HTMLElement {
        return div
    }

    actual fun setText(text: String) {
        div.innerHTML = htmlify(text)
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