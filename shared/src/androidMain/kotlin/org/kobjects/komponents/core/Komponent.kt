package org.kobjects.komponents.core

import android.view.View


actual abstract class Komponent {

   abstract fun getView(): View

   actual fun setBackgroundColor(color: UInt) {
      getView().setBackgroundColor(color.toInt())
   }
}