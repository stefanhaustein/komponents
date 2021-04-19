package org.kobjects.komponents.core

import android.widget.SeekBar

actual class Slider actual constructor(
    context: Context,
    value: Int,
    changeListener: ((Slider) -> Unit)?
) : AbstractInput<Int, Slider>(changeListener) {

    var seekBar = SeekBar(context.context)

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