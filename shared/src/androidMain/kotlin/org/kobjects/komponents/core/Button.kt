package org.kobjects.komponents.core

import android.view.Gravity
import android.view.View
import androidx.appcompat.widget.AppCompatButton

actual class Button actual constructor(
    context: Context,
    label: String,
    listener: ((Button) -> Unit)?) : Widget() {

    actual var label: String
        get() = button.text.toString()
        set(value) {
            button.text = value
        }

    actual var image: Svg? = null
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

    private val button = AppCompatButton(context.context)
    private val clickListeners = mutableListOf<(Button) -> Unit>()

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

    actual fun addClickListener(listener: (Button) -> Unit) {
        clickListeners.add(listener)
    }

    actual fun removeClickListener(listener: (Button) -> Unit) {
        clickListeners.remove(listener)
    }

}