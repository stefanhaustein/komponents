package org.kobjects.komponents.core

import org.kobjects.komponents.core.grid.GridLayout
import org.kobjects.komponents.core.recognizer.GestureRecognizer
import org.w3c.dom.DOMMatrix
import org.w3c.dom.DOMPointInit
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

   var parentImpl: GridLayout? = null

   actual fun setBackgroundColor(color: UInt) {
      val alpha = (color shr 24).toDouble() / 255.0
      val red = (color shr 16) and 255u
      val green = (color shr 8) and 255u
      val blue = color and 255u
      getElement().style.backgroundColor = "rgba($red,$green,$blue,$alpha)"
   }

   abstract fun getElement(): HTMLElement

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
      override var scaleX: Double = 1.0
         set(value) {
            field = value
            update()
         }
      override var scaleY: Double = 1.0
         set(value) {
            field = value
            update()
         }
      override var scale: Double?
         get() = if (scaleX == scaleY) scaleX else null
         set(value) {
            scaleX = value ?: 1.0
            scaleY = value ?: 1.0
         }

      var matrixImpl: DOMMatrix? = null
      var inverseMatrixImpl: DOMMatrix? = null

      override fun transform(x: Double, y: Double): Pair<Double, Double> {
         val transformed = getMatrix().transformPoint(DOMPointInit(x, y))
         return Pair(transformed.x, transformed.y)
      }

      override fun unTransform(x: Double, y: Double): Pair<Double, Double> {
         val transformed = getInverseMatrix().transformPoint(DOMPointInit(x, y))
         return Pair(transformed.x, transformed.y)
      }

      fun update() {
         matrixImpl = null
         inverseMatrixImpl = null
         getElement().style.transform = "translate(${x}px,${y}px) rotate(${rotation}deg) scale($scaleX,$scaleY)"
      }

      fun getMatrix(): DOMMatrix {
         var matrix = matrixImpl
         if (matrix == null) {
            matrix = DOMMatrix()
            matrix.translateSelf(x, y)
            matrix.rotateSelf(rotation)
            matrix.scaleSelf(scaleX, scaleY)
            matrixImpl = matrix
         }
         return matrix
      }

      fun getInverseMatrix(): DOMMatrix {
         var matrix = inverseMatrixImpl
         if (matrix == null) {
            matrix = getMatrix().inverse()
            inverseMatrixImpl = matrix
         }
         return matrix
      }
   }

   actual fun addGestureRecognizer(gestureRecognizer: GestureRecognizer) {
      gestureRecognizer.attach(this)
   }

   actual fun getParent(): GridLayout? {
      return parentImpl
   }

   fun fromClientCoordinates(clientX: Double, clientY: Double): Pair<Double, Double> {
      val parent = getParent()
      if (parent == null) {
         val rect = getElement().getBoundingClientRect()
         return Pair(clientX - rect.left, clientY - rect.top)
      }
      val parentPosition = parent.fromClientCoordinates(clientX, clientY)
      return fromParentCoordinates(parentPosition.first, parentPosition.second)
   }

}