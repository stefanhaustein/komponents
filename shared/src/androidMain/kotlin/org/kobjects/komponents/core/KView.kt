package org.kobjects.komponents.core

import android.view.View


actual abstract class KView {

   abstract fun getView(): View

   actual fun setBackgroundColor(color: UInt) {
      getView().setBackgroundColor(color.toInt())
   }

   actual fun setRotation(deg: Double) {
      getView().rotation = deg.toFloat()
   }
}