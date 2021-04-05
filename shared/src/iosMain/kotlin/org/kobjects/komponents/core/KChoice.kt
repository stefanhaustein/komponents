package org.kobjects.komponents.core

import platform.UIKit.*

actual class KChoice actual constructor(
    kontext: Kontext,
    options: List<String>,
    selectionListener: ((KChoice) -> Unit)?
) : KView() {
    private val button = UIButton.buttonWithType(UIButtonTypeSystem)
    private val selectionListeners = mutableListOf<(KChoice) -> Unit>()

    actual var selectedIndex: Int = 0
        set(value) {
            field = value
            button.setTitle((if (options.isEmpty()) "" else options[value]), UIControlStateNormal)
        }

   actual var options: List<String> = listOf()
        set(value) {
            field = value
            selectedIndex = 0
            val children = value.mapIndexed {index, value ->
                UIAction.actionWithTitle(title = value, identifier = null, image= null, handler = {
                    selectedIndex = index
                    for (listener in selectionListeners) {
                        listener(this@KChoice)
                    }})
            }
            button.menu = UIMenu.menuWithChildren(children)
            button.showsMenuAsPrimaryAction = true
        }

    actual fun addSelectionListener(selectionListener: (KChoice) -> Unit) {
        selectionListeners.add(selectionListener)
    }

    init {
        this.options = options
        if (selectionListener != null) {
            addSelectionListener(selectionListener)
        }
    }

    override fun getView(): UIView {
        return button
    }

    actual fun removeSelectionListener(selectionListener: (KChoice) -> Unit) {
        selectionListeners.remove(selectionListener)
    }

}