package com.cosmoloj.kt.unit.simple.impl

import com.cosmoloj.kt.unit.simple.api.AbstractUnit
import com.cosmoloj.kt.unit.simple.api.DerivedUnit
import com.cosmoloj.kt.unit.simple.api.UnitConverter
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test


class SimpleUnitOperatorTest {

    @Test
    fun transformedUnitConversion() {
        val m: AbstractUnit = SimpleFundamentalUnit()
        val km: AbstractUnit = m * 1000.0
        val cm: AbstractUnit = m / 100.0
        val cmToKm: UnitConverter = cm .. km
        Assertions.assertEquals(0.00003, cmToKm.convert(3.0), 1e-10)
        Assertions.assertEquals(3.0, (!cmToKm).convert(0.00003), 1e-10)
    }

    @Test
    fun derivedUnitConversion() {
        val m: AbstractUnit = SimpleFundamentalUnit()
        val km: AbstractUnit = m * 1000.0
        val km2: AbstractUnit = km % 2
        val cm: AbstractUnit = m / 100.0
        val cm2: AbstractUnit = cm % 2
        val km2Tocm2: UnitConverter = km2 .. cm2
        Assertions.assertEquals(30000000000.0, km2Tocm2.convert(3.0), 1e-10)
        Assertions.assertEquals(3.0, (!km2Tocm2).convert(30000000000.0), 1e-10)
    }

    @Test
    fun combinedDimensionDerivedUnitConversion() {
        val m: AbstractUnit = SimpleFundamentalUnit()
        val kg: AbstractUnit = SimpleFundamentalUnit()
        val g: AbstractUnit = kg / 1000.0
        val ton: AbstractUnit = kg * 1000.0
        val gPerM2: AbstractUnit = g / (m%2)
        val km: AbstractUnit = m * 1000.0
        val tonPerKm2: AbstractUnit = ton * (!km%2)
        val cm: AbstractUnit = m / 100.0
        val tonPerCm2: AbstractUnit = ton / (cm%2)
        val gPerM2ToTonPerKm2: UnitConverter = gPerM2 .. tonPerKm2
        val gPerM2ToTonPerCm2: UnitConverter = tonPerCm2 ..< gPerM2
        Assertions.assertEquals(1.0, gPerM2ToTonPerKm2.convert(1.0), 1e-10)
        Assertions.assertEquals(3.0, (!gPerM2ToTonPerKm2).convert(3.0), 1e-10)
        Assertions.assertEquals(1e-10, gPerM2ToTonPerCm2.convert(1.0), 1e-20)
        Assertions.assertEquals(3e-10, gPerM2ToTonPerCm2.convert(3.0), 1e-20)
        Assertions.assertEquals(0.0, gPerM2ToTonPerCm2.offset())
        Assertions.assertEquals(1e-10, gPerM2ToTonPerCm2.scale(), 1e-10)
        Assertions.assertEquals(-0.0, (!gPerM2ToTonPerCm2).offset())
        Assertions.assertEquals(3.0, (!gPerM2ToTonPerCm2).convert(3e-10), 1e-10)
    }

    @Test
    fun temperatures() {
        val k: AbstractUnit = SimpleFundamentalUnit()
        val c: AbstractUnit = k + 273.15
        val kToC: UnitConverter = k .. c
        Assertions.assertEquals(-273.15, kToC.convert(0.0), 1e-10)
        Assertions.assertEquals(273.15, (!kToC).convert(0.0), 1e-10)

        // en combinaison avec d'autres unités, les conversions d'unités de températures doivent devenir linéaires
        val m: AbstractUnit = SimpleFundamentalUnit()
        val cPerM: AbstractUnit = c / m
        val kPerM: AbstractUnit = k / m
        val kPerMToCPerM: UnitConverter = kPerM .. cPerM
        Assertions.assertEquals(3.0, kPerMToCPerM.convert(3.0), 1e-10)
        Assertions.assertEquals(3.0, (!kPerMToCPerM).convert(3.0), 1e-10)
    }

    @Test
    fun speed() {
        val m: AbstractUnit = SimpleFundamentalUnit()
        val km: AbstractUnit = m * 1000.0
        val s: AbstractUnit = SimpleFundamentalUnit()
        val h: AbstractUnit = s * 3600.0
        val ms: AbstractUnit = m / s
        val kmh: AbstractUnit = km / h
        val msToKmh: UnitConverter = ms .. kmh
        Assertions.assertEquals(360.0, msToKmh.convert(100.0), 1e-10)
        Assertions.assertEquals(5.0, (!msToKmh).convert(18.0), 1e-10)
    }
}
