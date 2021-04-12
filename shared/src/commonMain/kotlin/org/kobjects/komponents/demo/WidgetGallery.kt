package org.kobjects.komponents.demo

import org.kobjects.komponents.core.*
import org.kobjects.twemoji.TwemojiSvg

class WidgetGallery(kontext: Kontext) : Demo(kontext) {
    override val view: KView

    val titleSvg = KImage.createSvg(kontext, TwemojiSvg.SOCCER_BALL)
    val buttonSvg = KImage.createSvg(
        kontext,
        """
                <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 22 22">
                  <rect x="1" y="1" width="12" height="12" fill="none" stroke="black" stroke-width="2"/>
                  <rect x="9" y="9" width="12" height="12" fill="none" stroke="black" stroke-width="2"/>
                </svg>
            """.trimIndent()
    )

    init {
        val grid = KGridLayout(kontext)

        grid.padding = 4.0
        grid.gap = 4.0

        grid.columnTemplate = listOf(Size.fr(1.0))

        val image = KImageView(kontext)
//        image.setBackgroundColor(0x88888888u)
        image.image = titleSvg

        val textView = KTextView(kontext, "(Nothing selected)")
        grid.add(Cell(textView))

        grid.add(Cell(image, width = 100.0, height = 100.0, justify = Align.CENTER))

        val choice = KChoice<String>(kontext)
        choice.addChangeListener {
            textView.text = it.value + " selected"
        }
        choice.options = listOf("Choice 1", "Choice 2", "Choice 3")

        grid.add(Cell(choice))
        grid.add(Cell(KButton(kontext, "Button") {textView.text = "Button pressed"}))

        val buttonWithImage = KButton(kontext, "  With Image") {
            textView.text = "Image button pressed"
        }
        buttonWithImage.image = buttonSvg
        buttonWithImage.textAlignment = TextAlignment.LEFT
        grid.add(Cell(buttonWithImage))

        grid.add(Cell(KSlider(kontext) {
            textView.text = "Slider position: ${it.value}"
        }))
        grid.add(Cell(KCheckBox(kontext) {
            textView.text = if (it.value) "CheckBox checked" else "CheckBox unchecked"
        }))
        grid.add(Cell(KTextInput(kontext, "Text Input") {
            textView.text = it.value
        }))

        grid.add(Cell(KTextView(kontext, """Lorem ipsum dolor sit amet, consectetur adipisici elit, sed eiusmod tempor incidunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquid ex ea commodi consequat. Quis aute iure reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint obcaecat cupiditat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum""")))
        view = grid;
    }
}