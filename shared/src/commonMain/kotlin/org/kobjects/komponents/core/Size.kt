package org.kobjects.komponents.core

data class Size(val unit: Unit, val value: Double) {

    enum class Unit {
        PX, FR, AUTO
    }

    companion object {
        val AUTO = Size(Unit.AUTO, 0.0);

        fun px(value: Double): Size {
            return Size(Unit.PX, value)
        }

        fun fr(value: Double): Size {
            return Size(Unit.FR, value)
        }

        fun auto(): Size {
            return AUTO
        }
    }


}