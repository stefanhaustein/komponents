package org.kobjects.komponents.core

import android.view.View
import org.kobjects.komponents.core.mobile.ChildLayout

actual class KGridLayout actual constructor(kontext: Kontext) : KView(), Iterable<GridArea> {

    actual var columnGap = 0.0
        set(value) { field = value; layout.requestLayout() }
    actual var rowGap = 0.0
        set(value) { field = value; layout.requestLayout() }
    actual var paddingLeft = 0.0
        set(value) { field = value; layout.requestLayout() }
    actual var paddingTop = 0.0
        set(value) { field = value; layout.requestLayout() }
    actual var paddingBottom = 0.0
        set(value) { field = value; layout.requestLayout() }
    actual var paddingRight = 0.0
        set(value) { field = value; layout.requestLayout() }
    actual var justifyContent = Align.START
        set(value) { field = value; layout.requestLayout() }
    actual var alignContent = Align.START
        set(value) { field = value; layout.requestLayout() }
    actual var justifyItems = Align.STRETCH
        set(value) { field = value; layout.requestLayout() }
    actual var alignItems = Align.STRETCH
        set(value) { field = value; layout.requestLayout() }
    actual var columnTemplate = listOf<Size>()
        set(value) { field = value; layout.requestLayout() }
    actual var rowTemplate = listOf<Size>()
        set(value) { field = value; layout.requestLayout() }
    actual val size: Int
        get() = children.size

    private val layout = GridLayout(kontext.context, this)

    val children = mutableListOf<ChildLayout>()


    override fun getView(): View {
       return layout
    }

    actual fun add(positioned: GridArea) {
        positioned.gridLayout = this
        val childLayout = layout.LayoutParams(positioned);
        children.add(childLayout)
        layout.addView(positioned.view.getView(), childLayout)
    }

    actual var autoColumns: Size = Size.AUTO

    actual var autoRows: Size = Size.AUTO

    actual fun notifyAreaChanged(area: GridArea) {
        layout.requestLayout()
    }

    actual fun get(index: Int): GridArea {
        return children[index].positioned
    }

    override fun iterator(): Iterator<GridArea> {
        return children.map{it.positioned}.iterator()
    }

}