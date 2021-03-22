package org.kobjects.komponents.demo

import org.kobjects.komponents.core.*

class Demo() {

    fun alignDemo(kontext: Kontext): KGrid {
        val container = KGrid(kontext)

        container.setColumnWidth(0, Size.fr(1.0), repeat = Align.values().size)
        container.setRowHeight(0, Size.fr(1.0), repeat = Align.values().size)

        container.rowGap = 4.0
        container.columnGap = 4.0

        for (vAlign in Align.values()) {
            for (hAlign in Align.values()) {
                var tc = KTextView(kontext)
                tc.setText("v: $vAlign\nh: $hAlign")
                tc.setBackgroundColor(0xffeeeeeeU)

                container.add(
                    Positioned(
                        tc,
                        row = hAlign.ordinal,
                        column = vAlign.ordinal,
                        horizontalAlign = hAlign,
                        verticalAlign = vAlign
                    )
                )
            }
        }
        return container
    }
}