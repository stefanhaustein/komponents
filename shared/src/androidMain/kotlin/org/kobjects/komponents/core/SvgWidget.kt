package org.kobjects.komponents.core

import android.view.View
import android.widget.ImageView

actual class SvgWidget actual constructor(
    context: Context,
    image: Svg?
) : Widget() {
    private val imageView = ImageView(context.context)

    actual var image: Svg? = null
        set(value) {
            field = value
            imageView.setImageDrawable(value?.createDrawable(imageView.getContext()))
        }

    init {
        this.image = image
    }

    override fun getView(): View {
        return imageView
    }
}