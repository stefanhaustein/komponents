package org.kobjects.komponents.core

import android.view.View
import android.widget.SeekBar

actual class KSlider actual constructor(kontext: Kontext, value: Double) : KView() {

    var seekBar = SeekBar(kontext.context)

    actual var value: Double
        get() {
            return seekBar.progress.toDouble()
        }
        set(value) {
            seekBar.progress = value.toInt()
        }

    override fun getView() = seekBar
}