package org.kobjects.komponents.core

expect class Svg {
    companion object {
        fun createSvg(context: Context, code: String): Svg
    }

}