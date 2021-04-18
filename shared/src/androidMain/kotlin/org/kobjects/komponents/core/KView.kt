package org.kobjects.komponents.core

import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.core.view.GestureDetectorCompat
import org.kobjects.komponents.core.recognizer.DragRecognizer
import org.kobjects.komponents.core.recognizer.DragState
import org.kobjects.komponents.core.recognizer.GestureRecognizer


actual abstract class KView {

   actual val transformation: Transformation by lazy {
      TransformationImpl()
   }

   actual val clientX: Double
      get() = getView().context.pxToPt(getView().x)
   actual val clientY: Double
      get() = getView().context.pxToPt(getView().y)
   actual val clientWidth: Double
      get() = getView().context.pxToPt(getView().width)
   actual val clientHeight: Double
      get() = getView().context.pxToPt(getView().height)

   val gestureRecognizers = mutableListOf<GestureRecognizer>()

   abstract fun getView(): View

   private fun getContext() = getView().context

   actual fun setBackgroundColor(color: UInt) {
      getView().setBackgroundColor(color.toInt())
   }

   inner class TransformationImpl : Transformation {
      override var rotation: Double
         get() = getView().rotation.toDouble()
         set(value) {
            getView().rotation = value.toFloat()
         }

      override var x: Double
         get() = getContext().pxToPt(getView().translationX)
         set(value) {
            getView().translationX = getContext().ptToPxFloat(value)
         }

      override var y: Double
         get() = getContext().pxToPt(getView().translationY)
         set(value) {
            getView().translationY = getContext().ptToPxFloat(value)
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
         System.out.println("onSingleTapUp: $e")
         return false
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
                  DragState.START,
                  DragState.UPDATE -> DragState.UPDATE
                  else -> DragState.START
               }
               scrolling = true

               if (e1 != null && e2 != null) {
                  recognizer.distanceX = getContext().pxToPt(e2.rawX - e1.rawX)
                  recognizer.distanceY = getContext().pxToPt(e2.rawY - e1.rawY)
               } else {
                  recognizer.distanceX = 0.0
                  recognizer.distanceY = 0.0
               }
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
         return false
      }

      fun onEnd() {
         if (scrolling) {
            scrolling = false
            gestureRecognizers
               .filterIsInstance<DragRecognizer>()
               .forEach { recognizer ->
                  recognizer.state = DragState.END
                  recognizer.update(recognizer)
               }
         }
      }
   }

}
