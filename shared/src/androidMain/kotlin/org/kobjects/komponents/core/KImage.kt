package org.kobjects.komponents.core

import android.content.Context
import android.graphics.drawable.Drawable
import com.caverock.androidsvg.SVG

actual class KImage(val svg: SVG) {

    actual companion object {
        actual fun createSvg(code: String): KImage {
            return KImage(SVG.getFromString(code))
        }
    }

    fun createDrawable(context: Context): Drawable {
        return SVGDrawable(svg, context)
    }

}