package org.kobjects.komponents.core

import platform.QuartzCore.CALayer
import platform.UIKit.UIView
import cocoapods.SwiftSVG.*
import kotlinx.cinterop.ObjCAction
import platform.Foundation.NSRunLoop
import platform.Foundation.NSRunLoopCommonModes
import platform.Foundation.NSRunLoopMode
import platform.QuartzCore.CADisplayLink

actual class Kontext(
    val svgHelper: SvgHelper
){
    var displayLink: CADisplayLink? = null
    var animationCallbacks = mutableListOf<() -> Unit>()

    @ObjCAction
    fun step() {
        val currentCallbacks = animationCallbacks
        animationCallbacks = mutableListOf()
        currentCallbacks.forEach { it() }
    }

    actual fun requestAnimationFrame(callback: () -> Unit) {
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
}