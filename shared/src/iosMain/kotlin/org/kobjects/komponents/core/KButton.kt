package org.kobjects.komponents.core

import kotlinx.cinterop.ObjCAction
import platform.UIKit.*
import platform.objc.sel_registerName

actual class KButton actual constructor(kontext: Kontext) : KView() {

    val uiButton = UIButton.buttonWithType(
        UIButtonTypeSystem
        //UIButtonTypeRoundedRect
    )

    private var listener: (KButton) -> Unit = {}


    init {


    }

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
    fun clicked(whatever: Any?) {
        listener(this)
    }

    actual fun addClickListener(listener: (KButton) -> Unit) {
        this.listener = listener
         uiButton.addTarget(this, sel_registerName("clicked"), UIControlEventTouchUpInside)

        uiButton.targetForAction(sel_registerName("clicked"), uiButton)
    }

    fun tappedButton(sender: UIButton) {

    }

}