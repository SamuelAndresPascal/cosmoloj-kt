package com.cosmoloj.kt.unit.simple.impl

import com.cosmoloj.kt.unit.simple.api.AbstractUnit
import com.cosmoloj.kt.unit.simple.api.DerivedUnit
import com.cosmoloj.kt.unit.simple.api.Factor


open class SimpleFactor private constructor(
    private val dim: AbstractUnit,
    private val numerator: Int,
    private val denominator: Int
) : Factor {

    override fun dim(): AbstractUnit = this.dim

    override fun numerator(): Int  = this.numerator

    override fun denominator(): Int = this.denominator

    override operator fun times(value: Any): AbstractUnit = SimpleDerivedUnit.of(this, value as Factor)
    override operator fun div(value: Any): AbstractUnit
    = SimpleDerivedUnit.of(this, SimpleFactor.of(dim=value as Factor, numerator=-1))

    companion object {
        fun of(dim: Factor, numerator: Int, denominator: Int = 1): Factor {
            return if (dim is AbstractUnit)
                SimpleFactor(dim, numerator, denominator)
            else
                SimpleFactor(dim.dim(), numerator * dim.numerator(), denominator * dim.denominator())
        }
    }
}
