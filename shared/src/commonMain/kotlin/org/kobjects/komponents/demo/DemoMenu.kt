package org.kobjects.komponents.demo

import org.kobjects.komponents.core.*

class DemoMenu(
    val kontext: Kontext,
    val select: (DemoEnum, KView) -> Unit) {
    val view: KView

    var currentAnimation: (() -> Unit)? = null
    var animationRunning = false

    init {
        val layout = KGridLayout(kontext)




        for (demo in DemoEnum.values()) {
            val button = KButton(kontext, demo.title)
            button.addClickListener {
                select(demo, renderDemo(demo))
            }
            layout.add(GridArea(button))
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

    fun renderDemo(selector: DemoEnum): KView {
        val nextDemo = selector.render(kontext)
        currentAnimation = nextDemo.animation
        if (currentAnimation != null && !animationRunning) {
            animationRunning = true
            kontext.requestAnimationFrame { animate() }
        }
        return nextDemo.view
    }

    fun animate() {
        val animation = currentAnimation
        if (animation == null) {
            animationRunning = false
        } else {
            animation()
            kontext.requestAnimationFrame{ animate() }
        }
    }


}