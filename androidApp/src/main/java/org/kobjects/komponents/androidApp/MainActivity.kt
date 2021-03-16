package org.kobjects.komponents.androidApp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import org.kobjects.komponents.core.*
import org.kobjects.komponents.demo.alignDemo


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val context = Kontext(this)

        val textComponent = KTextView(context)
        textComponent.setText("Hello World")

        val svgKomponent = KImageView(context)
        svgKomponent.setContent("""<svg viewBox='0 0 100 100'>
            | <circle cx="50" cy="50" r="40" fill="red" /> 
            |</svg>""".trimMargin())

        val container = alignDemo(context)

        setContentView(container.getView())

    }
}
