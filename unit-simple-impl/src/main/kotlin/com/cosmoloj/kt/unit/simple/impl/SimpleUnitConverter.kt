package com.cosmoloj.kt.unit.simple.impl

import com.cosmoloj.kt.unit.simple.api.UnitConverter
import kotlin.math.pow


class SimpleUnitConverter : UnitConverter {

    private val scale: Double
    private val offset: Double
    private val inverse: UnitConverter

    private constructor(scale: Double, offset: Double, inverse: UnitConverter) {
        this.scale = scale
        this.offset = offset
        this.inverse = inverse
    }
    private constructor(scale: Double, offset: Double) {
        this.scale = scale
        this.offset = offset
        this.inverse = SimpleUnitConverter(1.0 / scale, -offset / scale, this);
    }


    override fun scale(): Double = this.scale

    override fun offset(): Double = this.offset

    override fun inverse(): UnitConverter = this.inverse

    override fun linear(): UnitConverter {
        // comparaison stricte volontaire sur un flottant
        return if (offset == 0.0) {
            this
        } else {
            linear(scale)
        }
    }

    override fun linearPow(pow: Double): UnitConverter {
        // comparaison stricte volontaire sur des flottants
        return if (offset == 0.0 && pow == 1.0) {
            this
        } else {
            linear(scale.pow(pow))
        }
    }

    override fun convert(value: Double): Double = scale * value + offset

    override fun concatenate(unitConverter: UnitConverter): UnitConverter {
        return of(unitConverter.scale() * scale, convert(unitConverter.offset()))
    }

    companion object {

        private val IDENTITY = linear(1.0)

        fun of(scale: Double, offset: Double): UnitConverter = SimpleUnitConverter(scale, offset)

        fun linear(scale: Double): UnitConverter = SimpleUnitConverter(scale, 0.0)

        fun translation(offset: Double): UnitConverter = SimpleUnitConverter(1.0, offset)

        fun identity(): UnitConverter = IDENTITY
    }
}