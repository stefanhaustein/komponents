package org.kobjects.komponents.core

import android.view.View


actual abstract class KView {

   abstract fun getView(): View

   actual fun setBackgroundColor(color: UInt) {
      getView().setBackgroundColor(color.toInt())
   }

   actual val transformation: Transformation by lazy {
      TransformationImpl()
   }

   inner class TransformationImpl : Transformation {
      override var rotation: Double
         get() = getView().rotation.toDouble()
         set(value) {
            getView().rotation = value.toFloat()
         }

      override var x: Double
         get() = getView().x.toDouble()
         set(value) {
            getView().x = value.toFloat()
         }

      override var y: Double
         get() = getView().y.toDouble()
         set(value) {
            getView().y = value.toFloat()
         }

   }


}