package org.kobjects.komponents.core.grid

import org.kobjects.komponents.core.Widget
import org.kobjects.komponents.core.Context
import org.kobjects.komponents.core.grid.mobile.MeasurementMode
import org.kobjects.komponents.core.grid.mobile.applyGridLayout
import platform.UIKit.UIView
import platform.UIKit.addSubview
import platform.UIKit.removeFromSuperview
import platform.UIKit.setNeedsLayout

actual class GridLayout actual constructor(context: Context) : Widget(), Iterable<Position> {
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


    actual fun get(index: Int) = children[index]

    private val uiGridView = IosGridView(this)

    val children = mutableListOf<Position>()

    actual fun addCell(
        view: Widget,
        column: Int?,
        row: Int?,
        columnSpan: Int,
        rowSpan: Int,
        width: Double?,
        height: Double?,
        align: Align?,
        justify: Align?): Cell {
        view.parentImpl = this
        val cell = Cell(this, view, column, row, columnSpan, rowSpan, width, height, align, justify)
        children.add(cell)
        uiGridView.addSubview(cell.view.getView())
        return cell
    }


    actual fun addAbsolute(
        view: Widget,
        top: Double?,
        right: Double?,
        bottom: Double?,
        left: Double?,
        width: Double?,
        height: Double?
    ): Absolute {
        view.parentImpl = this
        val absolute = Absolute(this, view, top, right, bottom, left, width, height)
        children.add(absolute)
        uiGridView.addSubview(absolute.view.getView())
        return absolute
    }

    actual fun remove(widget: Widget) {
        widget.parentImpl = null
        children.remove(widget)
        widget.getView().removeFromSuperview()
    }

    override fun getView(): UIView {
        return uiGridView
    }

    fun notifyPositionChanged() {
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
            children.filterIsInstance<Cell>(),
            widthMode,
            availableWidth,
            heightMode,
            availableHeight,
            measureOnly)
    }

    override fun iterator(): Iterator<Position> = children.iterator()


}