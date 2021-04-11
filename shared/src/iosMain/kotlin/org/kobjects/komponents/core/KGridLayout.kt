package org.kobjects.komponents.core

import kotlinx.cinterop.useContents
import org.kobjects.komponents.core.mobile.MeasurementMode
import org.kobjects.komponents.core.mobile.applyGridLayout
import platform.CoreGraphics.CGFLOAT_MAX
import platform.CoreGraphics.CGSizeMake
import platform.UIKit.UIView
import platform.UIKit.addSubview
import platform.UIKit.setNeedsLayout
import platform.UIKit.sizeThatFits

actual class KGridLayout actual constructor(kontext: Kontext) : KView(), Iterable<GridArea> {
    actual var columnGap = 0.0
        set(value) { field = value; uiGridView.setNeedsLayout() }
    actual var rowGap = 0.0
        set(value) { field = value; uiGridView.setNeedsLayout() }
    actual var autoColumns = Size.AUTO
        set(value) { field = value; uiGridView.setNeedsLayout() }
    actual var autoRows = Size.AUTO
        set(value) { field = value; uiGridView.setNeedsLayout() }
    actual var paddingLeft = 0.0
        set(value) { field = value; uiGridView.setNeedsLayout() }
    actual var paddingTop = 0.0
        set(value) { field = value; uiGridView.setNeedsLayout() }
    actual var paddingRight = 0.0
        set(value) { field = value; uiGridView.setNeedsLayout() }
    actual var paddingBottom = 0.0
        set(value) { field = value; uiGridView.setNeedsLayout() }
    actual var alignContent = Align.START
        set(value) { field = value; uiGridView.setNeedsLayout() }
    actual var justifyContent = Align.START
        set(value) { field = value; uiGridView.setNeedsLayout() }
    actual var alignItems = Align.STRETCH
        set(value) { field = value; uiGridView.setNeedsLayout() }
    actual var justifyItems = Align.STRETCH
        set(value) { field = value; uiGridView.setNeedsLayout() }
    actual var columnTemplate: List<Size> = listOf()
        set(value) { field = value; uiGridView.setNeedsLayout() }
    actual var rowTemplate: List<Size> = listOf()
        set(value) { field = value; uiGridView.setNeedsLayout() }
    actual val size: Int
        get() = children.size
    actual var gap: Double?
        get() = if (columnGap == rowGap) columnGap else null
        set(value) {
            if (value != null) {
                columnGap = value
                rowGap = value
            }
        }
    actual var padding: Double?
        get() = if (paddingLeft == paddingRight && paddingRight == paddingTop && paddingTop == paddingBottom) paddingLeft else null
        set(value) {
            if (value != null) {
                paddingLeft = value
                paddingRight = value
                paddingTop = value
                paddingBottom = value
            }
        }


    actual fun get(index: Int) = children[index].positioned

    private val uiGridView = IosGridView(this)

    val children = mutableListOf<IosChildLayout>()


    actual fun add(positioned: GridArea) {
        positioned.gridLayout = this
        children.add(IosChildLayout(positioned))
        uiGridView.addSubview(positioned.view.getView())
    }

    override fun getView(): UIView {
        return uiGridView
    }

    actual fun notifyAreaChanged(area: GridArea) {
        uiGridView.setNeedsLayout()
    }

    override fun layout(
        availableWidth: Double,
        widthMode: MeasurementMode,
        availableHeight: Double,
        heightMode: MeasurementMode,
        measureOnly: Boolean
    ) : Pair<Double,Double> {
        return applyGridLayout(
            this,
            children,
            widthMode,
            availableWidth,
            heightMode,
            availableHeight,
            measureOnly)
    }

    override fun iterator(): Iterator<GridArea> = children.map{ it.positioned }.iterator()


}