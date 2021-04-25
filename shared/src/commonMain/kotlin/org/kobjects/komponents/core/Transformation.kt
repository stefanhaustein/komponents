package org.kobjects.komponents.core

interface Transformation {
    var rotation: Double
    var x: Double
    var y: Double

    fun transform(x: Double, y: Double): Pair<Double, Double>
    fun unTransform(x: Double, y: Double): Pair<Double, Double>
}