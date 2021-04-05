package org.kobjects.komponents.core

import android.view.View
import android.widget.ImageView
import com.caverock.androidsvg.SVG
import com.caverock.androidsvg.SVGImageView

actual class KImageView actual constructor(
    kontext: Kontext,
    image: KImage?
) : KView() {
    private val imageView = ImageView(kontext.context)

    actual var image: KImage? = null
        set(value) {
            field = value
            imageView.setImageDrawable(value?.createDrawable())
        }

    init {
        this.image = image
    }

    override fun getView(): View {
        return imageView
    }
}