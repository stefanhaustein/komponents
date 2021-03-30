package org.kobjects.komponents.core

import platform.QuartzCore.CALayer
import cocoapods.SwiftSVG.*
import platform.CoreGraphics.CGRect

interface SvgHelper {

    fun resizeToFit(svgLayer: SVGLayer, width: Double, height: Double)
    fun createLayer(xml: String, callback: (SVGLayer) -> Unit)

}