package org.kobjects.komponents.core

import org.kobjects.komponents.core.recognizer.GestureRecognizer
import org.w3c.dom.HTMLElement


actual abstract class Widget {

   actual val transformation: Transformation by lazy {
      TransformationImpl()
   }

   actual val offsetLeft: Double
      get() = getElement().offsetLeft.toDouble()
   actual val offsetTop: Double
      get() = getElement().offsetTop.toDouble()
   actual val offsetWidth: Double
      get() = getElement().offsetWidth.toDouble()
   actual val offsetHeight: Double
      get() = getElement().offsetHeight.toDouble()

   actual var opacity: Double = 1.0
      set(value) {
         field = value
         getElement().style.opacity = value.toString()
      }

   actual fun setBackgroundColor(color: UInt) {
      val alpha = (color shr 24).toDouble() / 255.0
      val red = (color shr 16) and 255u
      val green = (color shr 8) and 255u
      val blue = color and 255u
      getElement().style.backgroundColor = "rgba($red,$green,$blue,$alpha)"
   }

   abstract fun getElement(): HTMLElement

   // TODO: Implement
   inner class TransformationImpl : Transformation {
      override var rotation: Double = 0.0
         set(value) {
            field = value
            update()
         }
      override var x: Double = 0.0
         set(value) {
            field = value
            update()
         }
      override var y: Double = 0.0
         set(value) {
            field = value
            update()
         }

      fun update() {
         getElement().style.transform = "translate(${x}px,${y}px) rotate(${rotation}deg)"
      }
   }

   actual fun addGestureRecognizer(gestureRecognizer: GestureRecognizer) {
      gestureRecognizer.attach(this)
   }

}