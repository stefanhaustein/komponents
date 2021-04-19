package org.kobjects.komponents.core

import platform.UIKit.*

actual class DropdownList<T> actual constructor(
    context: Context,
    options: List<T>,
    var stringify: (T) -> String,
    changeListener: ((DropdownList<T>) -> Unit)?
) : AbstractInput<T, DropdownList<T>>(changeListener) {

    private val button = UIButton.buttonWithType(UIButtonTypeSystem)

    override var value: T
        get() {
            return options[selectedIndex]
        }
        set(value) {
            selectedIndex = options.indexOf(value)
        }

    actual var selectedIndex: Int = 0
        set(value) {
            field = value
            button.setTitle((if (options.isEmpty()) "" else stringify(options[value])), UIControlStateNormal)
        }

   actual var options: List<T> = listOf()
        set(value) {
            field = value
            selectedIndex = 0
            val children = value.mapIndexed {index, value ->
                UIAction.actionWithTitle(
                    title = stringify(value),
                    identifier = null,
                    image= null,
                    handler = {
                        selectedIndex = index
                        notifyChangeListeners()
                    })
            }
            button.menu = UIMenu.menuWithChildren(children)
            button.showsMenuAsPrimaryAction = true
        }

    override fun getView(): UIView {
        return button
    }

    init {
        this.options = options
    }
}

