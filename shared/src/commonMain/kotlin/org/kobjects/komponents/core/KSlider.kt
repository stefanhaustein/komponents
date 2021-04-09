package org.kobjects.komponents.core

expect class KSlider(kontext: Kontext, value: Double = 0.0) : KView {
    var value: Double
}