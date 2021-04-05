package org.kobjects.komponents.core

import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner

actual class KChoice actual constructor(
    kontext: Kontext,
    options: List<String>,
    selectionListener: ((KChoice) -> Unit)?
) : KView() {
    private val spinner = Spinner(kontext.context)
    private val selectionListeners = mutableListOf<(KChoice) -> Unit>()

    actual var options: List<String> = listOf()
        set(value) {
            field = value
            withoutListeners {
                spinner.adapter = ArrayAdapter(
                    spinner.context,
                    android.R.layout.simple_spinner_item, options
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

    init {
        this.options = options
        if (selectionListener != null) {
            addSelectionListener(selectionListener)
        }
    }

    private fun withoutListeners(action: () -> Unit) {
        spinner.onItemSelectedListener = null

        action()

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                selectionListeners.forEach { it(this@KChoice) }
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectionListeners.forEach { it(this@KChoice) }
            }
        }
    }

    override fun getView(): View {
        return spinner
    }

    actual fun addSelectionListener(selectionListener: (KChoice) -> Unit) {
        selectionListeners.add(selectionListener)
    }

    actual fun removeSelectionListener(selectionListener: (KChoice) -> Unit) {
        selectionListeners.remove(selectionListener)
    }

}