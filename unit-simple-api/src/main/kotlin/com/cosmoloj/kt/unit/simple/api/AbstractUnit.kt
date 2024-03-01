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

    override operator fun times(value: Any): AbstractUnit

    override operator fun div(value: Any): AbstractUnit

    operator fun rem(value: Int): AbstractUnit

    operator fun rangeTo(unit: AbstractUnit): UnitConverter = getConverterTo(unit)

    operator fun rangeUntil(unit: AbstractUnit): UnitConverter = unit.getConverterTo(this)

    operator fun not(): AbstractUnit = rem(-1)
}
