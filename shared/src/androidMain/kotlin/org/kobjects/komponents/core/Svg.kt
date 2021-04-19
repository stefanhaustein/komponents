package org.kobjects.komponents.core

import android.content.Context
import android.graphics.drawable.Drawable
import com.caverock.androidsvg.SVG

actual class Svg(val svg: SVG) {

    actual companion object {
        actual fun createSvg(context: org.kobjects.komponents.core.Context, code: String): Svg {
            return Svg(SVG.getFromString(code))
        }
    }

    fun createDrawable(context: Context): Drawable {
        return SVGDrawable(svg, context)
    }

}