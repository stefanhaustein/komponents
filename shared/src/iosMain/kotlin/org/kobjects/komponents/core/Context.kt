package org.kobjects.komponents.core

import platform.QuartzCore.CALayer
import kotlinx.cinterop.ObjCAction
import platform.Foundation.NSRunLoop
import platform.Foundation.NSRunLoopCommonModes
import platform.Foundation.NSRunLoopMode
import platform.QuartzCore.CACurrentMediaTime
import platform.QuartzCore.CADisplayLink
import platform.UIKit.*

actual class Context(
    val controller: UIViewController,
    val svgHelper: SvgHelper
){
    var displayLink: CADisplayLink? = null
    var animationCallbacks = mutableListOf<(Double) -> Unit>()

    @ObjCAction
    fun step() {
        val currentCallbacks = animationCallbacks
        animationCallbacks = mutableListOf()
        val t0 = CACurrentMediaTime()
        currentCallbacks.forEach { it(t0) }
    }

    actual fun requestAnimationFrame(callback: (Double) -> Unit) {
        if (displayLink == null) {
            displayLink = CADisplayLink.displayLinkWithTarget(
                this,
                platform.objc.sel_registerName("step"))
            displayLink!!.preferredFramesPerSecond = 0
            displayLink!!.addToRunLoop(
                runloop = NSRunLoop.currentRunLoop(),
                forMode = NSRunLoopCommonModes)
        }
        animationCallbacks.add(callback)
    }

    actual fun alert(
        title: String,
        okAction: Action,
        cancelAction: Action?
    ) {
        var alert = UIAlertController.alertControllerWithTitle(
            title = title, message = null, preferredStyle = UIAlertControllerStyleAlert)
        alert.addAction(UIAlertAction.actionWithTitle(
            title = okAction.title,
            style = UIAlertActionStyleDefault) {okAction.handler(okAction)})
        if (cancelAction != null) {
            alert.addAction(UIAlertAction.actionWithTitle(
                title = cancelAction.title,
                style = UIAlertActionStyleCancel) {cancelAction.handler(cancelAction)})
        }
        controller.presentViewController(alert, animated = true, completion = null)
    }

    actual fun getTimestamp(): Double {
        return CACurrentMediaTime()
    }
}