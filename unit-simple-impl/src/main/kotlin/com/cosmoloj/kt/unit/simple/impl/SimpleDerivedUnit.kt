package com.cosmoloj.kt.unit.simple.impl

import com.cosmoloj.kt.unit.simple.api.DerivedUnit
import com.cosmoloj.kt.unit.simple.api.Factor
import com.cosmoloj.kt.unit.simple.api.UnitConverter


class SimpleDerivedUnit private constructor(
    private val definition: List<Factor>
) : SimpleUnit(), DerivedUnit {

    override fun definition(): List<Factor> = definition

    override fun toBase(): UnitConverter {
        /*
        En combinaison avec d'autres unités, il ne faut plus appliquer de décalage d'origine d'échelle (température)
        mais uniquement appliquer le facteur d'échelle.
        */
        var transform: UnitConverter = SimpleUnitConverter.identity()
        for (factor in definition) {
            transform = factor.dim().toBase().linearPow(factor.power()).concatenate(transform)
        }
        return transform
    }

    companion object {
        fun of(def: List<Factor>): DerivedUnit = SimpleDerivedUnit(def)

        fun of(vararg def: Factor): DerivedUnit = of(def.asList())
    }
}
