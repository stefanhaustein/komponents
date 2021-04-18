package org.kobjects.komponents.core

import org.kobjects.komponents.core.recognizer.GestureRecognizer
import org.w3c.dom.HTMLElement


actual abstract class KView {

   actual val transformation: Transformation by lazy {
      TransformationImpl()
   }

   actual val clientX: Double
      get() = getElement().clientTop.toDouble()
   actual val clientY: Double
      get() = getElement().clientLeft.toDouble()
   actual val clientWidth: Double
      get() = getElement().clientWidth.toDouble()
   actual val clientHeight: Double
      get() = getElement().clientHeight.toDouble()

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