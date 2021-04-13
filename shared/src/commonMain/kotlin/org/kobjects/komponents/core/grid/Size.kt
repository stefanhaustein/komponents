package org.kobjects.komponents.core.grid

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

    override fun toString(): String {
        return if (unit == Unit.AUTO) "auto" else "${value}${unit.toString().toLowerCase()}"
    }

}