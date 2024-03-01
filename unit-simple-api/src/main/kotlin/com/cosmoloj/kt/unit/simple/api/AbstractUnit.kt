package com.cosmoloj.kt.unit.simple.api

interface AbstractUnit : Factor {

    fun getConverterTo(target: AbstractUnit): UnitConverter = target.toBase().inverse().concatenate(toBase())

    fun toBase(): UnitConverter

    override fun dim() = this

    override fun numerator(): Int = 1

    override fun denominator(): Int = 1

    fun shift(value: Double): TransformedUnit

    fun scaleMultiply(value: Double): TransformedUnit

    fun scaleDivide(value: Double): TransformedUnit

    fun factor(numerator: Int, denominator: Int = 1): Factor

    operator fun plus(value: Double) : TransformedUnit = shift(value)

    operator fun minus(value: Double): TransformedUnit = shift(-value)

    operator fun times(value: Double): TransformedUnit = scaleMultiply(value)

    operator fun div(value: Double): TransformedUnit = scaleDivide(value)

    operator fun rem(value: Int): Factor = factor(value)
}
