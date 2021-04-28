package org.kobjects.komponents.demo

import org.kobjects.komponents.core.*
import org.kobjects.komponents.core.grid.Align
import org.kobjects.komponents.core.grid.GridLayout
import org.kobjects.komponents.core.grid.Size
import org.kobjects.komponents.core.recognizer.DragRecognizer
import org.kobjects.komponents.core.recognizer.GestureState
import org.kobjects.komponents.core.recognizer.TapRecognizer
import org.kobjects.twemoji.TwemojiSvg

class WidgetGallery(context: Context) : Demo(context) {
    override val view: Widget
    var t0 = 0.0

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
    var bouncing = mutableListOf<BouncingState>()

    var velocityX = 0.0
    var velocityY = 0.0
    var lastTimeStamp = 0.0

    val grid = GridLayout(context)

    override val animation: ((Double) -> Unit) = {
        val dt = minOf(0.1, it - t0)
        t0 = it

        for (i in bouncing.indices.reversed()) {
            val ball = bouncing[i]
            ball.widget.transformation.x += ball.dx * dt

            if (ball.widget.transformation.x < 0 && ball.dx < 0
                || ball.widget.transformation.x + 100.0 > grid.offsetWidth && ball.dx > 0) {
                ball.dx = -ball.dx
            }

            ball.widget.transformation.y += ball.dy * dt

            if (ball.widget.transformation.y > grid.offsetHeight - 100.0 && ball.dy > 0) {
                ball.dy = -ball.dy
                ball.widget.transformation.y = grid.offsetHeight - 100.0
            } else {
                ball.dy += dt * 1000
            }

            if (ball.tapped) {
                if (ball.widget.opacity > 0.03) {
                    ball.widget.opacity -= 0.03
                } else {
                    bouncing.removeAt(i)
                    grid.remove(ball.widget)
                }
            }

        }
    }


    init {

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
                GestureState.UPDATE,
                GestureState.START -> {
                    val distance = it.translation(grid)
                    val dt = it.timestamp - lastTimeStamp
                    velocityX = (distance.first - dragging.transformation.x) / dt
                    velocityY = (distance.second - dragging.transformation.y) / dt
                    println("velocity: "+ velocityX + "/" + velocityY)
                    dragging.transformation.x = distance.first
                    dragging.transformation.y = distance.second
                    lastTimeStamp = it.timestamp
                }
                GestureState.END -> {
                    val copy = SvgWidget(context, dragging.image)
                    copy.transformation.rotation = dragging.transformation.rotation
                    grid.addAbsolute(
                        copy,
                        left = 0.0,
                        top = 0.0,
                        width = 100.0,
                        height = 100.0)

                    val state = BouncingState(copy, velocityX, velocityY)
                    copy.addGestureRecognizer(TapRecognizer{
                        state.tapped = true
                    })
                    bouncing.add(state)

                    copy.transformation.x = dragging.offsetLeft + dragging.transformation.x
                    copy.transformation.y = dragging.offsetTop + dragging.transformation.y

                    dragging.transformation.x = 0.0
                    dragging.transformation.y = 0.0

                } else -> {
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

        val buttonWithImage = Button(context, "  Clear all") {
            textView.text = "Image button pressed"
            if (bouncing.isEmpty()) {
                context.alert("No balls active.", Action("Ok") {})
            } else {
                context.alert(
                    "Clear all Balls?",
                    Action("Ok") { bouncing.forEach { it.tapped = true } },
                    Action("Cancel") {})
            }
        }
        buttonWithImage.image = buttonSvg
        buttonWithImage.textAlignment = TextAlignment.LEFT
        grid.addCell(buttonWithImage)

        grid.addCell(Slider(context) {
            textView.text = "Slider position: ${it.value}"
            dragging.transformation.rotation = it.value * 3.6
            image.transformation.rotation = dragging.transformation.rotation
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

    class BouncingState(
        val widget: SvgWidget,
        var dx: Double,
        var dy: Double
    ) {
        var tapped = false
    }
}