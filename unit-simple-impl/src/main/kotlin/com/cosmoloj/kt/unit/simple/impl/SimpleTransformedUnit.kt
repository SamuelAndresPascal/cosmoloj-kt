package com.cosmoloj.kt.unit.simple.impl

import com.cosmoloj.kt.unit.simple.api.AbstractUnit
import com.cosmoloj.kt.unit.simple.api.TransformedUnit
import com.cosmoloj.kt.unit.simple.api.UnitConverter


class SimpleTransformedUnit private constructor(
    private val toRef: UnitConverter,
    private val ref: AbstractUnit
) : SimpleUnit(), TransformedUnit {

    override fun reference(): AbstractUnit = this.ref

    override fun toReference(): UnitConverter = this.toRef

    companion object {
        fun of(toRef: UnitConverter, ref: AbstractUnit): TransformedUnit =
            SimpleTransformedUnit(toRef, ref)
    }

    override fun toBase(): UnitConverter = this.reference().toBase().concatenate(this.toReference())
}