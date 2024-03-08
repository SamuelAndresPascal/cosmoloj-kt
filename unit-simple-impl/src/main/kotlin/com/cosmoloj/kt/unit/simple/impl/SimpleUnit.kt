package com.cosmoloj.kt.unit.simple.impl

import com.cosmoloj.kt.unit.simple.api.AbstractUnit
import com.cosmoloj.kt.unit.simple.api.Factor
import com.cosmoloj.kt.unit.simple.api.TransformedUnit

abstract class SimpleUnit : AbstractUnit {
    override fun shift(value: Double): TransformedUnit =
        SimpleTransformedUnit.of(SimpleUnitConverter.translation(value), this)

    override fun scaleMultiply(value: Double): TransformedUnit =
        SimpleTransformedUnit.of(SimpleUnitConverter.linear(value), this)

    override fun scaleDivide(value: Double): TransformedUnit = this.scaleMultiply(1.0 / value)

    override fun factor(numerator: Int, denominator: Int): Factor = SimpleFactor.of(this, numerator)

    override operator fun rem(value: Int): AbstractUnit = SimpleDerivedUnit.of(factor(value))

    override operator fun times(value: Any): AbstractUnit =
        if (value is Number)
            scaleMultiply(value.toDouble())
        else SimpleDerivedUnit.of(this, value as Factor)

    override operator fun div(value: Any): AbstractUnit =
        if (value is Number)
            scaleDivide(value.toDouble())
        else SimpleDerivedUnit.of(this, SimpleFactor.of(value as Factor, -1))

}
