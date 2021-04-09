package org.kobjects.komponents.core

import platform.UIKit.UIView

interface SvgHelper {
    fun createView(xml: String): UIView
    fun updateView(view: UIView, xml: String)
}