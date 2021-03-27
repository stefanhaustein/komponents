package org.kobjects.komponents.androidApp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import org.kobjects.komponents.core.*
import org.kobjects.komponents.demo.Demo


class MainActivity : AppCompatActivity() {
    lateinit var demo: Demo
    var isRoot = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val context = Kontext(this)

        val textComponent = KTextView(context)
        textComponent.setText("Hello World")

        val svgKomponent = KImageView(context)
        svgKomponent.setImage(KImage.createSvg("""<svg viewBox='0 0 100 100'>
            | <circle cx="50" cy="50" r="40" fill="red" /> 
            |</svg>""".trimMargin()))

        demo = Demo(context) { selector, kView ->
            setTitle(selector.title)
            setContentView(kView.getView())
            isRoot = false
        }
        showMenu()
    }

    fun showMenu() {
        setTitle("Komponents Demo")
        setContentView(demo.renderMenu().getView())
        isRoot = true
    }

    override fun onBackPressed() {
        if (isRoot) {
            super.onBackPressed()
        } else {
            showMenu()
        }
    }

}
