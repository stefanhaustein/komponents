package org.kobjects.komponents.core

import android.view.View
import android.widget.SeekBar

actual class KSlider actual constructor(
    kontext: Kontext,
    value: Int,
    changeListener: ((KSlider) -> Unit)?
) : AbstractInputView<Int, KSlider>(changeListener) {

    var seekBar = SeekBar(kontext.context)

    override var value: Int
        get() {
            return seekBar.progress
        }
        set(value) {
            seekBar.progress = value
        }

    override fun getView() = seekBar

    init {
        this.value = value
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser){
                    notifyChangeListeners()
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })
    }
}