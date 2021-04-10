package org.kobjects.komponents.core


import platform.UIKit.UIAction
import platform.UIKit.UIControlEventValueChanged
import platform.UIKit.UITextView
import platform.UIKit.UITextViewDelegateProtocol
import platform.darwin.NSObject

actual class KTextInput actual constructor(
    kontext: Kontext,
    value: String,
    changeListener: ((KTextInput) -> Unit)?
) : AbstractInputView<String, KTextInput>(changeListener) {

    var textView = UITextView()

    override var value: String
        get() {
            return textView.text.toString()
        }
        set(value) {
            textView.setText(value)
        }


    override fun getView() = textView

    init {
        this.value = value
        textView.delegate = object : NSObject(), UITextViewDelegateProtocol {
            override fun textViewDidChange(textView: UITextView) {
                notifyChangeListeners()
            }
        }
    }

}