package org.kobjects.komponents.core

import android.view.View
import android.widget.Button

actual class KButton actual constructor(
    kontext: Kontext,
    label: String,
    listener: ((KButton) -> Unit)?) : KView() {

    actual var label: String
        get() = button.text.toString()
        set(value) {
            button.text = value
        }

    actual var image: KImage? = null
        set(value) {
            field = value
            button.setCompoundDrawablesWithIntrinsicBounds(
                value?.createDrawable(), null, null, null)
        }

    private val button = Button(kontext.context)
    private val clickListeners = mutableListOf<(KButton) -> Unit>()

    init {
        button.text = label
        button.minHeight = 0
        button.setOnClickListener { clickListeners.forEach{ it(this) } }
        if (listener != null) {
            addClickListener(listener)
        }
    }

    override fun getView(): View {
        return button;
    }

    actual fun addClickListener(listener: (KButton) -> Unit) {
        clickListeners.add(listener)
    }

    actual fun removeClickListener(listener: (KButton) -> Unit) {
        clickListeners.remove(listener)
    }
}