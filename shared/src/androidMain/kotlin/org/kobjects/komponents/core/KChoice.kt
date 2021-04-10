package org.kobjects.komponents.core

import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner

actual class KChoice<T> actual constructor(
    kontext: Kontext,
    options: List<T>,
    val stringify: (T) -> String,
    changeListener: ((KChoice<T>) -> Unit)?
) : AbstractInputView<T, KChoice<T>>(changeListener) {
    private val spinner = Spinner(kontext.context)

    override var value
        get() = options[selectedIndex]
        set(value) {
            selectedIndex = options.indexOf(value)
        }

    actual var options: List<T> = listOf()
        set(value) {
            field = value
            withoutListeners {
                spinner.adapter = ArrayAdapter(
                    spinner.context,
                    android.R.layout.simple_spinner_item, value.map { stringify(it) }
                ).also {
                    it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                }
            }
        }
    actual var selectedIndex: Int
        get() = spinner.selectedItemPosition
        set(value) {
            withoutListeners {
                spinner.setSelection(value)
            }
        }


    private fun withoutListeners(action: () -> Unit) {
        spinner.onItemSelectedListener = null

        action()

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                notifyChangeListeners()
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                notifyChangeListeners()
            }
        }
    }

    override fun getView(): View {
        return spinner
    }

    init {
        this.options = options
    }

}