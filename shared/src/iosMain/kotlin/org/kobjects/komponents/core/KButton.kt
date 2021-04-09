package org.kobjects.komponents.core

import kotlinx.cinterop.ObjCAction
import platform.UIKit.*
import platform.objc.sel_registerName

actual class KButton actual constructor(
    val kontext: Kontext,
    label: String,
    listener: ((KButton) -> Unit)?
) : KView() {

    private val uiButton = UIButton.buttonWithType(UIButtonTypeSystem)
    private var listeners: MutableList<(KButton) -> Unit> = mutableListOf()

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

    init {
        this.label = label
        uiButton.addTarget(
            this,
            platform.objc.sel_registerName("clicked"),
            platform.UIKit.UIControlEventTouchUpInside
        )
    }


    override fun getView(): UIView {
       return uiButton
    }

    @ObjCAction
    fun clicked() {
        listeners.forEach { it(this) }
    }

    actual fun addClickListener(listener: (KButton) -> Unit) {
        listeners.add(listener)
    }

    actual fun removeClickListener(listener: (KButton) -> Unit) {
        listeners.remove(listener)
    }


}