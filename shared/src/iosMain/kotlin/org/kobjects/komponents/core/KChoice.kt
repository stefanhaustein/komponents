package org.kobjects.komponents.core

import platform.UIKit.*

actual class KChoice actual constructor(kontext: Kontext) :
    KView() {

    val button = UIButton.buttonWithType(UIButtonTypeSystem)
    val listeners = mutableListOf<(KChoice, Int, String) -> Unit>()
    var data = listOf<String>()
    var selectedIndex = 0

    actual fun setData(data: List<String>) {
        this.data = data
        setSelectedIndex(0)

        val children = data.mapIndexed {index, value ->
            UIAction.actionWithTitle(title = value, identifier = null, image= null, handler = {
                setSelectedIndex(index)
                for (listener in listeners) {
                    listener(this@KChoice, index, value)
                }
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

    actual fun setSelectedIndex(index: Int) {
        button.setTitle((if (data.isEmpty()) "" else data[index]), UIControlStateNormal)
        this.selectedIndex = index
    }

}