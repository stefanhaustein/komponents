package org.kobjects.komponents.core

import platform.UIKit.*

actual class KButton actual constructor(
    val kontext: Kontext,
    label: String,
    listener: ((KButton) -> Unit)?
) : KView() {

    private val uiButton = UIButton.buttonWithType(
        UIButtonTypeSystem,
        primaryAction = UIAction.actionWithTitle(
            label,
            image = null,
            identifier = null,
            handler = {listeners.forEach {it(this)}}))
    private var listeners: MutableList<(KButton) -> Unit> =
        if (listener == null)  mutableListOf() else mutableListOf(listener)

    actual var label = ""
        set(value) {
            field = value
            uiButton.setTitle(label, UIControlStateNormal)
        }
    actual var image: KImage? = null
        set(value) {
            field = value
            val uiImage = if (value == null) null else kontext.svgHelper.getUIImage(value.svgImage)
            uiButton.setImage(uiImage, UIControlStateNormal)
        }
    actual var textAlignment = TextAlignment.CENTER
        set(value) {
            field = value
        }


    override fun getView(): UIView {
       return uiButton
    }


    actual fun addClickListener(listener: (KButton) -> Unit) {
        listeners.add(listener)
    }

    actual fun removeClickListener(listener: (KButton) -> Unit) {
        listeners.remove(listener)
    }


}