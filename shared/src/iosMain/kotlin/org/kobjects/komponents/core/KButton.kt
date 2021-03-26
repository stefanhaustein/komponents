package org.kobjects.komponents.core

import kotlinx.cinterop.ObjCAction
import platform.UIKit.*
import platform.objc.sel_registerName

actual class KButton actual constructor(kontext: Kontext) : KView() {

    val uiButton = UIButton.buttonWithType(
        UIButtonTypeSystem
    )

    private var listeners: MutableList<(KButton) -> Unit>? = null


    actual fun setImage(image: KImage) {
        // tbd
    }

    actual fun setText(text: String) {
        uiButton.setTitle(text, UIControlStateNormal)
    }

    override fun getView(): UIView {
       return uiButton
    }

    @ObjCAction
    fun clicked() {
        val listeners = this.listeners;
        if (listeners != null) {
            for (listener in listeners) {
                listener(this)
            }
        }
    }

    actual fun addClickListener(listener: (KButton) -> Unit) {
        val listeners = this.listeners;
        if (listeners == null) {
            this.listeners = mutableListOf(listener)
            uiButton.addTarget(this, sel_registerName("clicked"), UIControlEventTouchUpInside)
        } else {
            listeners.add(listener)
        }
    }
}