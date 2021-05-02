package org.kobjects.komponents.core

import android.graphics.Matrix
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.core.view.GestureDetectorCompat
import org.kobjects.komponents.core.grid.GridLayout
import org.kobjects.komponents.core.recognizer.DragRecognizer
import org.kobjects.komponents.core.recognizer.GestureRecognizer
import org.kobjects.komponents.core.recognizer.GestureState
import org.kobjects.komponents.core.recognizer.TapRecognizer


actual abstract class Widget {

   actual val transformation: Transformation by lazy {
      TransformationImpl()
   }

   actual val offsetLeft: Double
      get() = getView().context.pxToPt(getView().x) - transformation.x
   actual val offsetTop: Double
      get() = getView().context.pxToPt(getView().y) - transformation.y
   actual val offsetWidth: Double
      get() = getView().context.pxToPt(getView().width)
   actual val offsetHeight: Double
      get() = getView().context.pxToPt(getView().height)

   actual var opacity: Double
      get() = getView().alpha.toDouble()
      set(value) { getView().alpha = value.toFloat() }

   actual var hidden: Boolean
      get() = getView().visibility == View.VISIBLE
      set(value) {
         getView().visibility = if (value) View.VISIBLE else View.INVISIBLE
      }

   actual var zIndex: Int
      get() = getView().translationZ.toInt()
      set(value) {
         getView().translationZ = value.toFloat()
      }

   val gestureRecognizers = mutableListOf<GestureRecognizer>()

   var parentImpl: GridLayout? = null

   abstract fun getView(): View

   private fun getContext() = getView().context

   actual fun setBackgroundColor(color: UInt) {
      getView().setBackgroundColor(color.toInt())
   }

   inner class TransformationImpl : Transformation {

      var matrixImpl: Matrix? = null
      var inverseMatrixImpl: Matrix? = null

      override var rotation: Double
         get() = getView().rotation.toDouble()
         set(value) {
            invalidateMatrixes()
            getView().rotation = value.toFloat()
         }

      override var x: Double
         get() = getContext().pxToPt(getView().translationX)
         set(value) {
            invalidateMatrixes()
            getView().translationX = getContext().ptToPxFloat(value)
         }

      override var y: Double
         get() = getContext().pxToPt(getView().translationY)
         set(value) {
            invalidateMatrixes()
            getView().translationY = getContext().ptToPxFloat(value)
         }

      override var scaleX: Double
         get() = getView().scaleX.toDouble()
         set(value) {
            invalidateMatrixes()
            getView().scaleX = value.toFloat()
         }

      override var scaleY: Double
         get() = getView().scaleY.toDouble()
         set(value) {
            invalidateMatrixes()
            getView().scaleY = value.toFloat()
         }

      override var scale: Double?
         get() = if (getView().scaleX == getView().scaleY) getView().scaleX.toDouble() else null
         set(value) {
            invalidateMatrixes()
            getView().scaleX = value?.toFloat() ?: 0f
            getView().scaleY = value?.toFloat() ?: 0f
         }

      override fun transform(x: Double, y: Double): Pair<Double, Double> {
         val points = floatArrayOf(x.toFloat(), y.toFloat())
         getMatrix().mapPoints(points)
         return Pair(points[0].toDouble(), points[1].toDouble())
      }

      override fun unTransform(x: Double, y: Double): Pair<Double, Double> {
         val points = floatArrayOf(x.toFloat(), y.toFloat())
         getInverseMatrix().mapPoints(points)
         return Pair(points[0].toDouble(), points[1].toDouble())
      }

      fun getMatrix(): Matrix {
         var value = matrixImpl
         if (value == null) {
            value = Matrix()
            value.preTranslate(x.toFloat(), y.toFloat())
            value.preRotate(rotation.toFloat())
            matrixImpl = value
         }
         return value
      }

      fun getInverseMatrix(): Matrix {
         var value = inverseMatrixImpl
         if (value == null) {
            value = Matrix()
            getMatrix().invert(value)
            inverseMatrixImpl = value
         }
         return value
      }

      fun invalidateMatrixes() {
         matrixImpl = null
         inverseMatrixImpl = null;
      }

   }

   actual fun addGestureRecognizer(gestureRecognizer: GestureRecognizer) {
      if (gestureRecognizers.isEmpty()) {
         val gestureListener = GestureListener()
         val gestureDetector = GestureDetectorCompat(getView().context, gestureListener)
         gestureDetector.setIsLongpressEnabled(false)
         getView().setOnTouchListener { view, event ->
            if (!gestureDetector.onTouchEvent(event) && event.action == MotionEvent.ACTION_UP) {
               gestureListener.onEnd()
            }
            true
         }
      }
      gestureRecognizers.add(gestureRecognizer)
   }

   inner class GestureListener : GestureDetector.OnGestureListener {
      var scrolling = false

      override fun onDown(e: MotionEvent?): Boolean {
         System.out.println("onDown: $e")
         return true
      }

      override fun onShowPress(e: MotionEvent?) {
         System.out.println("onShowPress: $e")
      }

      override fun onSingleTapUp(e: MotionEvent?): Boolean {
         var consumed = false
         gestureRecognizers
            .filterIsInstance<TapRecognizer>()
            .forEach {
               it.recognized(it)
               consumed = true
            }
         return consumed
      }

      override fun onScroll(
         e1: MotionEvent?,
         e2: MotionEvent?,
         distanceX: Float,
         distanceY: Float
      ): Boolean {
         System.out.println("onScroll: $e1 $e2")

         var consumed = false
         gestureRecognizers
            .filterIsInstance<DragRecognizer>()
            .forEach { recognizer ->
               recognizer.state = when (recognizer.state) {
                  GestureState.START,
                  GestureState.UPDATE -> GestureState.UPDATE
                  else -> GestureState.START
               }
               scrolling = true
               recognizer.timestamp = (e2?.eventTime ?: 0) / 1000.0
               recognizer.velocityX = 0f
               recognizer.velocityY = 0f
               recognizer.rawStartX = e1?.rawX ?: 0f
               recognizer.rawStartY = e1?.rawY ?: 0f
               recognizer.rawCurrentX = e2?.rawX ?: 0f
               recognizer.rawCurrentY = e2?.rawY ?: 0f
               recognizer.update(recognizer)
               consumed = true
            }

         return consumed
      }

      override fun onLongPress(e: MotionEvent?) {
         System.out.println("onLongPress: $e")
      }

      override fun onFling(
         e1: MotionEvent?,
         e2: MotionEvent?,
         velocityX: Float,
         velocityY: Float
      ): Boolean {
         System.out.println("onFling: $e1 $e2")
         var consumed = false
         gestureRecognizers
            .filterIsInstance<DragRecognizer>()
            .forEach { recognizer ->
               recognizer.state = GestureState.END
               scrolling = false

               recognizer.velocityX = velocityX
               recognizer.velocityY = velocityY

               System.out.println("fling " + velocityX  +", " + velocityY)

               recognizer.update(recognizer)
               consumed = true
            }

         return consumed
      }

      fun onEnd() {
         if (scrolling) {
            scrolling = false
            gestureRecognizers
               .filterIsInstance<DragRecognizer>()
               .forEach { recognizer ->
                  recognizer.state = GestureState.END
                  recognizer.update(recognizer)
               }
         }
      }
   }

   actual fun getParent(): GridLayout? {
      return parentImpl
   }

   fun fromRawCoordinates(rawX: Float, rawY: Float): Pair<Double, Double> {
      val parent = getParent()
      if (parent == null) {
         val origin = intArrayOf(0, 0)
         getView().getLocationOnScreen(origin);
         return Pair(
            getView().context.pxToPt(rawX - origin[0].toFloat()),
            getView().context.pxToPt(rawY - origin[1].toFloat())
         )
      }
      val parentPosition = parent.fromRawCoordinates(rawX, rawY)
      return fromParentCoordinates(parentPosition.first, parentPosition.second)
   }

}
