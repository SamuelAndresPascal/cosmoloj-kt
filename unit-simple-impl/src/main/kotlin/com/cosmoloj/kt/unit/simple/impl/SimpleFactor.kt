package com.cosmoloj.kt.unit.simple.impl

import com.cosmoloj.kt.unit.simple.api.AbstractUnit
import com.cosmoloj.kt.unit.simple.api.Factor


class SimpleFactor private constructor(
    private val dim: AbstractUnit,
    private val numerator: Int,
    private val denominator: Int
) : Factor {

    override fun dim(): AbstractUnit = this.dim

    override fun numerator(): Int  = this.numerator

    override fun denominator(): Int = this.denominator

    companion object {
        fun of(dim: AbstractUnit, numerator: Int, denominator: Int = 1): Factor {
            return SimpleFactor(dim, numerator, denominator)
        }
    }
}