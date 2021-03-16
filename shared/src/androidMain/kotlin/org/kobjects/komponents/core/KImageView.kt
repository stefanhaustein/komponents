package org.kobjects.komponents.core

import android.view.View
import com.caverock.androidsvg.SVG
import com.caverock.androidsvg.SVGImageView

actual class KImageView actual constructor(kontext: Kontext) : KView() {

    val imageView = SVGImageView(kontext.context)

    override fun getView(): View {
        return imageView
    }

    actual fun setContent(svg: String) {
        imageView.setSVG(SVG.getFromString(svg))
    }
}