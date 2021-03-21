package org.kobjects.komponents.core

import org.w3c.dom.HTMLElement


actual abstract class KView {

   abstract fun getElement(): HTMLElement

   actual fun setBackgroundColor(color: UInt) {
      val alpha = (color shr 24).toDouble() / 255.0
      val red = (color shr 16) and 255u
      val green = (color shr 8) and 255u
      val blue = color and 255u
      getElement().style.backgroundColor = "rgba($red,$green,$blue,$alpha)"
   }
}