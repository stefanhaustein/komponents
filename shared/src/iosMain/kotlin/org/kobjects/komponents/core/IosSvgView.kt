package org.kobjects.komponents.core

import kotlinx.cinterop.ObjCAction
import platform.CoreGraphics.CGRectMake
import platform.QuartzCore.CALayer
import platform.UIKit.UIView
import cocoapods.SwiftSVG.*
import kotlinx.cinterop.useContents

class IosSvgView(
    val svgHelper: SvgHelper
    ) : UIView(CGRectMake(0.0, 0.0, 50.0, 50.0)) {

    var svgLayer: SVGLayer? = null


    @ObjCAction
    fun layoutSubviews() {
        val svgLayer = this.svgLayer
        if (svgLayer != null) {
            this.bounds.useContents {
                svgHelper.resizeToFit(svgLayer, this.size.width, this.size.height)
            }
        }
    }

    fun setSvgLayer(svgLayer: SVGLayer) {
        this.svgLayer?.removeFromSuperlayer()
        this.layer.addSublayer(svgLayer)
        this.svgLayer = svgLayer
        this.bounds.useContents {
            svgHelper.resizeToFit(svgLayer, this.size.width, this.size.height)
        }
    }


}