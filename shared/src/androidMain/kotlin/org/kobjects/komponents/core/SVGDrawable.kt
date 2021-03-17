package org.kobjects.komponents.core

import android.graphics.Canvas
import com.caverock.androidsvg.SVG
import android.graphics.drawable.PictureDrawable
import android.graphics.ColorFilter
import android.graphics.Rect
import android.graphics.drawable.Drawable
import com.caverock.androidsvg.RenderOptions

/**
 * Workaround for PictureDrawable scaling issues.
 */
class SVGDrawable(private val svg: SVG) : Drawable() {
    private var wrapped_: PictureDrawable? = null
    private var alpha_ = 0
    private var colorFilter_: ColorFilter? = null
    private fun getWrapped(): PictureDrawable? {
        if (wrapped_ == null) {
            onBoundsChange(bounds)
        }
        return wrapped_
    }

    override fun draw(canvas: Canvas) {
        getWrapped()!!.draw(canvas)
    }

    override fun setAlpha(i: Int) {
        alpha_ = i
        getWrapped()!!.alpha = i
    }

    override fun onBoundsChange(bounds: Rect) {
        val renderOptions = RenderOptions()
        renderOptions.viewPort(
            bounds.left.toFloat(),
            bounds.top.toFloat(),
            bounds.width().toFloat(),
            bounds.height().toFloat()
        )
        wrapped_ = PictureDrawable(svg.renderToPicture(renderOptions))
        wrapped_!!.bounds = bounds
        wrapped_!!.colorFilter = colorFilter
        wrapped_!!.alpha = alpha
    }

    override fun getIntrinsicWidth(): Int {
        return svg.documentWidth.toInt()
    }

    override fun getIntrinsicHeight(): Int {
        return svg.documentHeight.toInt()
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
        this.colorFilter = colorFilter
        getWrapped()!!.colorFilter = colorFilter
    }

    override fun getOpacity(): Int {
        return getWrapped()!!.opacity
    }
}