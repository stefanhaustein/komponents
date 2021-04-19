package org.kobjects.komponents.demo

import org.kobjects.komponents.core.*
import org.kobjects.komponents.core.grid.Align
import org.kobjects.komponents.core.grid.GridLayout
import org.kobjects.komponents.core.grid.Size

class DemoMenu(
    val context: Context,
    val select: (DemoEnum, Widget) -> Unit) {
    val view: Widget

    var currentAnimation: (() -> Unit)? = null
    var animationRunning = false

    init {
        val layout = GridLayout(context)




        for (demo in DemoEnum.values()) {
            val button = Button(context, demo.title)
            button.addClickListener {
                select(demo, renderDemo(demo))
            }
            layout.addCell(button)
        }


        layout.autoColumns = Size.fr(1.0)

        layout.justifyContent = Align.CENTER
        //   layout.setBackgroundColor(0xffff0000u)

        layout.paddingBottom = 24.0
        layout.paddingTop = 24.0

        layout.paddingRight = 12.0
        layout.paddingLeft = 12.0

        view = layout
    }

    fun renderDemo(selector: DemoEnum): Widget {
        val nextDemo = selector.render(context)
        currentAnimation = nextDemo.animation
        if (currentAnimation != null && !animationRunning) {
            animationRunning = true
            context.requestAnimationFrame { animate() }
        }
        return nextDemo.view
    }

    fun animate() {
        val animation = currentAnimation
        if (animation == null) {
            animationRunning = false
        } else {
            animation()
            context.requestAnimationFrame{ animate() }
        }
    }


}