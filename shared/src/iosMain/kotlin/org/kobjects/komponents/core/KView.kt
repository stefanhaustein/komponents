package org.kobjects.komponents.core

import platform.UIKit.UIColor
import platform.UIKit.UIView
import platform.UIKit.backgroundColor

abstract actual class KView {
    abstract fun getView(): UIView

    actual fun setBackgroundColor(color: UInt) {
        getView().backgroundColor = UIColor(
            red = ((color shr 16) and 255u).toDouble() / 255.0,
            green = ((color shr 8) and 255u).toDouble() / 255.0,
            blue = (color and 255u).toDouble() / 255.0,
            alpha = ((color shr 24) and 255u).toDouble() / 255.0)
    }
}