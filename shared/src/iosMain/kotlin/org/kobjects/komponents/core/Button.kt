package org.kobjects.komponents.core

import platform.UIKit.*

actual class Button actual constructor(
    val context: Context,
    label: String,
    listener: ((Button) -> Unit)?
) : Widget() {

    private val uiButton = UIButton.buttonWithType(
        UIButtonTypeSystem,
        primaryAction = UIAction.actionWithTitle(
            label,
            image = null,
            identifier = null,
            handler = {listeners.forEach {it(this)}}))
    private var listeners: MutableList<(Button) -> Unit> =
        if (listener == null)  mutableListOf() else mutableListOf(listener)

    actual var label = ""
        set(value) {
            field = value
            uiButton.setTitle(label, UIControlStateNormal)
        }
    actual var image: Svg? = null
        set(value) {
            field = value
            val uiImage = if (value == null) null else context.svgHelper.getUIImage(value.svgImage)
            uiButton.setImage(uiImage, UIControlStateNormal)
        }
    actual var textAlignment = TextAlignment.CENTER
        set(value) {
            field = value
        }


    override fun getView(): UIView {
       return uiButton
    }


    actual fun addClickListener(listener: (Button) -> Unit) {
        listeners.add(listener)
    }

    actual fun removeClickListener(listener: (Button) -> Unit) {
        listeners.remove(listener)
    }


}