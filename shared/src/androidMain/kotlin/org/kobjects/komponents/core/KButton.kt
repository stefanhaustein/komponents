package org.kobjects.komponents.core

import android.view.Gravity
import android.view.View
import android.widget.Button
import androidx.appcompat.widget.AppCompatButton
import com.google.android.material.button.MaterialButton

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

            val drawable = value?.createDrawable(button.getContext())!!
            System.out.println("svg intrinsic size: ${drawable.intrinsicWidth}. ${drawable.intrinsicHeight}")

            button.setCompoundDrawablesWithIntrinsicBounds(
                   drawable, null, null, null)
        }

    actual var textAlignment = TextAlignment.CENTER
        set(value) {
            field = value
            button.gravity = when (value) {
                TextAlignment.LEFT -> Gravity.LEFT or Gravity.CENTER_VERTICAL
                TextAlignment.RIGHT -> Gravity.RIGHT or Gravity.CENTER_VERTICAL
                else -> Gravity.CENTER
            }
        }

    private val button = AppCompatButton(kontext.context)
    private val clickListeners = mutableListOf<(KButton) -> Unit>()

    init {
        button.text = label
       // button.insetTop = 0
       // button.insetBottom = 0
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