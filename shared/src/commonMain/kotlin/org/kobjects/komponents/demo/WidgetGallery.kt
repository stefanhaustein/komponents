package org.kobjects.komponents.demo

import org.kobjects.komponents.core.*
import org.kobjects.twemoji.TwemojiSvg

class WidgetGallery(kontext: Kontext) : Demo(kontext) {
    override val view: KView

    val svg = KImage.createSvg("""
            <svg viewBox="0 0 30 30">
              <rect x="0" y="0" width="10" height="10" fill="#ff0000"/>
              <rect x="10" y="10" width="10" height="10" fill="#00ff00"/>
              <rect x="20" y="20" width="10" height="10" fill="#0000ff"/>
            </svg>
        """.trimIndent())

    init {
        val grid = KGridLayout(kontext)
        grid.columnTemplate = listOf(Size.fr(1.0))

        val image = KImageView(kontext)
        image.setBackgroundColor(0x88888888u)
        image.image = svg
        grid.add(GridArea(image, width = 100.0, height = 100.0, justify = Align.CENTER))

        grid.add(GridArea(KChoice(kontext, listOf("KChoice", "Alternative"))))
        grid.add(GridArea(KButton(kontext, "KButton")))

        val buttonWithImage = KButton(kontext, "  With Image")
        buttonWithImage.image = KImage.createSvg(TwemojiSvg.BLACK_CIRCLE)
        buttonWithImage.textAlignment = TextAlignment.LEFT
        grid.add(GridArea(buttonWithImage))


        grid.add(GridArea(KTextView(kontext, """
            Lorem ipsum dolor sit amet, consectetur adipisici elit, 
            sed eiusmod tempor incidunt ut labore et dolore magna aliqua. 
            Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris 
            nisi ut aliquid ex ea commodi consequat. Quis aute iure reprehenderit 
            in voluptate velit esse cillum dolore eu fugiat nulla pariatur. 
            Excepteur sint obcaecat cupiditat non proident, sunt in culpa qui 
            officia deserunt mollit anim id est laborum.
        """.trimIndent().replace("\n", " "))))
        view = grid;
    }
}