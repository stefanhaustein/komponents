package org.kobjects.komponents.core

import android.view.View
import android.widget.ImageView
import com.caverock.androidsvg.SVG
import com.caverock.androidsvg.SVGImageView

actual class KImageView actual constructor(kontext: Kontext) : KView() {

    private val imageView = ImageView(kontext.context)
    private var image: KImage? = null

    override fun getView(): View {
        return imageView
    }

    actual fun setImage(image: KImage) {
        this.image = image
        imageView.setImageDrawable(image.createDrawable())
    }
}