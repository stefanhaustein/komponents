package org.kobjects.komponents.androidApp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import org.kobjects.komponents.core.*
import org.kobjects.komponents.demo.DemoMenu


class MainActivity : AppCompatActivity() {
    lateinit var menu: DemoMenu
    var isRoot = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val context = Context(this)

        menu = DemoMenu(context) { selector, kView ->
            setTitle(selector.title)
            setContentView(kView.getView())
            isRoot = false
        }
        showMenu()
    }

    fun showMenu() {
        setTitle("Komponents Demo")
        setContentView(menu.view.getView())
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
