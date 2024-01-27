package com.cosmoloj.kt.unit.simple.impl

import com.cosmoloj.kt.unit.simple.api.FundamentalUnit
import com.cosmoloj.kt.unit.simple.api.UnitConverter


class SimpleFundamentalUnit: SimpleUnit(), FundamentalUnit {
    override fun toBase(): UnitConverter = SimpleUnitConverter.identity()
}
