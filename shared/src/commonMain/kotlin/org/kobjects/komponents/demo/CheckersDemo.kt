package org.kobjects.komponents.demo

import org.kobjects.komponents.core.*
import org.kobjects.komponents.core.grid.Align
import org.kobjects.komponents.core.grid.GridLayout
import org.kobjects.komponents.core.grid.Size
import org.kobjects.komponents.core.recognizer.DragRecognizer
import org.kobjects.komponents.core.recognizer.GestureState
import org.kobjects.twemoji.TwemojiSvg
import kotlin.math.pow
import kotlin.math.round

class CheckersDemo(context: Context) : Demo(context) {

    override val view: Widget
 //   override val animation: (() -> Unit)?

    init {
        val grid = GridLayout(context)
        grid.alignContent = Align.CENTER
        grid.justifyContent = Align.CENTER
        grid.alignItems = Align.CENTER
        grid.justifyItems = Align.CENTER
        grid.columnTemplate = listOf(Size.auto())
        grid.rowTemplate = listOf(Size.auto())

        val checkerboard = GridLayout(context)
        checkerboard.columnTemplate = List(8) { Size.px(40.0) }
        checkerboard.rowTemplate = List(8) { Size.px(40.0) }

        for (y in 0 until 8) {
            for (x in 0 until 8) {
                checkerboard.addCell(
                    Label(context).also {
                        it.setBackgroundColor(
                            if ((x + y) and 1 == 0) 0xffccccccu else 0xff777777u) },
                    column = x,
                    row = y)
            }
        }

        val black = Svg.createSvg(context, TwemojiSvg.BLACK_CIRCLE)
        val white = Svg.createSvg(context, TwemojiSvg.WHITE_CIRCLE)

        for (y in 0 until 8) {
            for (x in 0 until 8) {
                if ((y < 3 || y > 4) && (x + y) and 1 == 1) {
                    val imageView = SvgWidget(context, if (y < 3) black else white)
                    // imageView.setBackgroundColor(0xffff8888u)
                    val gridArea = checkerboard.addCell(
                        imageView,
                        column = x,
                        row = y)
                    imageView.addGestureRecognizer(DragRecognizer {
                        val distance = it.translation(imageView)

                        if (it.state == GestureState.END) {
                            imageView.transformation.x = 0.0
                            imageView.transformation.y = 0.0
                            if (y > 4) {
                                val newRow = round(gridArea.row!! + distance.second / 40.0).toInt()
                                val newCol = round(gridArea.column!! + distance.first / 40.0).toInt()
                                if (((newCol + newRow) and 1) == 1
                                    && newCol >= 0 && newRow > 2
                                    && newCol < 8 && newRow < 8) {
                                    gridArea.column = newCol
                                    gridArea.row = newRow
                                }
                            }
                        } else {
                            imageView.transformation.x =
                                if (y < 3) resist(distance.first) else distance.first
                            imageView.transformation.y =
                                if (y < 3) resist(distance.second) else distance.second
                        }})
                }
            }
        }


        grid.addCell(
            checkerboard,
            column = 0,
            row = 0,
            width = 8 * 40.0,   // Why -- shouldn't the template be sufficient?
            height = 8 * 40.0)

        view = grid

        /*
        animation = {
            checkerboard.transformation.rotation += 0.2
        }*/
    }

    fun resist(value: Double): Double {
        return if (value < 0) -resist(-value) else value.pow(1.0 / 3.0)
    }

}