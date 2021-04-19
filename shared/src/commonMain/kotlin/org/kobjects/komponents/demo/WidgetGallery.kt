package org.kobjects.komponents.demo

import org.kobjects.komponents.core.*
import org.kobjects.komponents.core.grid.Align
import org.kobjects.komponents.core.grid.GridLayout
import org.kobjects.komponents.core.grid.Size
import org.kobjects.komponents.core.recognizer.DragRecognizer
import org.kobjects.komponents.core.recognizer.DragState
import org.kobjects.twemoji.TwemojiSvg

class WidgetGallery(context: Context) : Demo(context) {
    override val view: Widget

    enum class BALL(val label: String, val svg: String) {
        BASEBALL("Baseball", TwemojiSvg.BASEBALL),
        BASKETBALL("Basketball", TwemojiSvg.BASKETBALL),
        VOLLEYBALL("Volleyball", TwemojiSvg.VOLLEYBALL),
        SOCCER("Soccer", TwemojiSvg.SOCCER_BALL),
        SOFTBALL("Softball", TwemojiSvg.SOFTBALL),
        TENNIS("Tennis", TwemojiSvg.TENNIS),
        POOL("Pool", TwemojiSvg.POOL_8_BALL)
    }


    val titleSvg = Svg.createSvg(context, TwemojiSvg.BASEBALL)
    val buttonSvg = Svg.createSvg(
        context,
        """
                <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 22 22">
                  <rect x="1" y="1" width="12" height="12" fill="none" stroke="black" stroke-width="2"/>
                  <rect x="9" y="9" width="12" height="12" fill="none" stroke="black" stroke-width="2"/>
                </svg>
            """.trimIndent()
    )
    var dragging = SvgWidget(context)

    init {
        val grid = GridLayout(context)

        grid.padding = 4.0
        grid.gap = 4.0

        grid.columnTemplate = listOf(Size.fr(1.0))

        val image = SvgWidget(context)
//        image.setBackgroundColor(0x88888888u)
        image.image = titleSvg
        dragging.image = titleSvg
        dragging.addGestureRecognizer(DragRecognizer{
            println("$it")
            when (it.state) {
                DragState.UPDATE,
                DragState.START -> {
                    dragging.transformation.x = it.distanceX
                    dragging.transformation.y = it.distanceY
                }
                else -> {
                    dragging.transformation.x = 0.0
                    dragging.transformation.y = 0.0
                }
            }
        })

        val textView = Label(context, "(Nothing selected)")
        grid.addCell(textView)

        val choice = DropdownList(context, BALL.values().toList(), stringify = {it.label}) {
            textView.text = "${it.value.label} selected"
            image.image = Svg.createSvg(context, it.value.svg)
            dragging.image = image.image
        }

        grid.addCell(choice)
        grid.addCell(Button(context, "Button") {textView.text = "Button pressed"})

        val buttonWithImage = Button(context, "  With Image") {
            textView.text = "Image button pressed"
        }
        buttonWithImage.image = buttonSvg
        buttonWithImage.textAlignment = TextAlignment.LEFT
        grid.addCell(buttonWithImage)

        grid.addCell(Slider(context) {
            textView.text = "Slider position: ${it.value}"
        })
        grid.addCell(CheckBox(context) {
            textView.text = if (it.value) "CheckBox checked" else "CheckBox unchecked"
        })
        grid.addCell(TextBox(context, "Text Input") {
            textView.text = it.value
        })

        grid.addCell(Label(context, """Lorem ipsum dolor sit amet, consectetur adipisici elit, sed eiusmod tempor incidunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquid ex ea commodi consequat. Quis aute iure reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint obcaecat cupiditat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum"""))
        view = grid;

        grid.addCell(image, row = 1, column = 0, width = 100.0, height = 100.0, justify = Align.CENTER)
        grid.addCell(dragging, row = 1, column = 0, width = 100.0, height = 100.0, justify = Align.CENTER)

    }
}