package org.kobjects.komponents.core

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.caverock.androidsvg.SVG
import com.caverock.androidsvg.SVGImageView

actual class SvgKomponent actual constructor(kontext: Kontext) : Komponent() {

    val imageView = SVGImageView(kontext.context)

    override fun getView(): View {
        return imageView
    }

    actual fun setContent(svg: String) {
        imageView.setSVG(SVG.getFromString(svg))
    }
}