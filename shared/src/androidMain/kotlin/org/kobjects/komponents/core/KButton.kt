package org.kobjects.komponents.core

import android.view.View
import android.widget.Button

actual class KButton actual constructor(kontext: Kontext, label: String) : KView() {

    private val button = Button(kontext.context).also { it.text = label }
    private var image: KImage? = null

    override fun getView(): View {
        return button;
    }

    actual fun setImage(image: KImage) {
        this.image = image
        button.setCompoundDrawablesWithIntrinsicBounds(image.createDrawable(), null, null, null)
    }

    actual fun setText(text: String) {
        button.text = text
    }

    actual fun addClickListener(listener: (KButton) -> Unit) {
        button.setOnClickListener{listener(this)}
    }
}