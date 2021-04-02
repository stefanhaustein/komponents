package org.kobjects.komponents.core

import platform.Foundation.NSString
import platform.UIKit.*
import platform.darwin.NSInteger

actual class KChoice actual constructor(kontext: Kontext) :
    KView() {

    val button = UIButton.buttonWithType(UIButtonTypeSystem)
    val listeners = mutableListOf<(KChoice, Int, String) -> Unit>()

    actual fun setData(data: List<String>) {
        button.setTitle((if (data.isEmpty()) "" else data[0]) + " \u25bc", UIControlStateNormal)

        val children = data.mapIndexed {index, value ->
            UIAction.actionWithTitle(title = value, identifier = null, image= null, handler = {
                button.setTitle(data[index], UIControlStateNormal)
            })
        }

        button.menu = UIMenu.menuWithChildren(children)
        button.showsMenuAsPrimaryAction = true
    }

    actual fun addSelectionListener(listener: (KChoice, Int, String) -> Unit) {
        listeners.add(listener)
    }

    override fun getView(): UIView {
        return button
    }

}