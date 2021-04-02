package org.kobjects.komponents.core

import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner

actual class KChoice actual constructor(kontext: Kontext) :
    KView() {

    val spinner = Spinner(kontext.context)
    val listeners = mutableListOf<(KChoice, Int, String) -> Unit>()

    init {
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                for (listener in listeners) {
                    listener(this@KChoice, -1, "")
                }
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                for (listener in listeners) {
                    listener(this@KChoice, position, spinner.adapter.getItem(position) as String)
                }
            }

        }
    }

    actual fun setData(data: List<String>) {
        spinner.adapter = ArrayAdapter(spinner.context,
        android.R.layout.simple_spinner_item, data).also {
            it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }

    }

    actual fun addSelectionListener(listener: (KChoice, Int, String) -> Unit) {
        listeners.add(listener)
    }

    override fun getView(): View {
        return spinner
    }

}