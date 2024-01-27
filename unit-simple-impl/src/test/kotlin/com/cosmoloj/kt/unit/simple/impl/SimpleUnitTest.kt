package com.cosmoloj.kt.unit.simple.impl

import com.cosmoloj.kt.unit.simple.api.AbstractUnit
import com.cosmoloj.kt.unit.simple.api.UnitConverter
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test


class SimpleUnitTest {

    @Test
    fun transformedUnitConversion() {
        val m: AbstractUnit = SimpleFundamentalUnit()
        val km: AbstractUnit = m.scaleMultiply(1000.0)
        val cm: AbstractUnit = m.scaleDivide(100.0)
        val cmToKm: UnitConverter = cm.getConverterTo(km)
        Assertions.assertEquals(0.00003, cmToKm.convert(3.0), 1e-10)
        Assertions.assertEquals(3.0, cmToKm.inverse().convert(0.00003), 1e-10)
    }

    @Test
    fun derivedUnitConversion() {
        val m: AbstractUnit = SimpleFundamentalUnit()
        val km: AbstractUnit = m.scaleMultiply(1000.0)
        val km2: AbstractUnit = SimpleDerivedUnit.of(km.factor(2))
        val cm: AbstractUnit = m.scaleDivide(100.0)
        val cm2: AbstractUnit = SimpleDerivedUnit.of(cm.factor(2))
        val km2Tocm2: UnitConverter = km2.getConverterTo(cm2)
        Assertions.assertEquals(30000000000.0, km2Tocm2.convert(3.0), 1e-10)
        Assertions.assertEquals(3.0, km2Tocm2.inverse().convert(30000000000.0), 1e-10)
    }

    @Test
    fun combinedDimensionDerivedUnitConversion() {
        val m: AbstractUnit = SimpleFundamentalUnit()
        val kg: AbstractUnit = SimpleFundamentalUnit()
        val g: AbstractUnit = kg.scaleDivide(1000.0)
        val ton: AbstractUnit = kg.scaleMultiply(1000.0)
        val gPerM2: AbstractUnit = SimpleDerivedUnit.of(g, m.factor(-2))
        val km: AbstractUnit = m.scaleMultiply(1000.0)
        val tonPerKm2: AbstractUnit = SimpleDerivedUnit.of(ton, km.factor(-2))
        val cm: AbstractUnit = m.scaleDivide(100.0)
        val tonPerCm2: AbstractUnit = SimpleDerivedUnit.of(ton, cm.factor(-2))
        val gPerM2ToTonPerKm2: UnitConverter = gPerM2.getConverterTo(tonPerKm2)
        val gPerM2ToTonPerCm2: UnitConverter = gPerM2.getConverterTo(tonPerCm2)
        Assertions.assertEquals(1.0, gPerM2ToTonPerKm2.convert(1.0), 1e-10)
        Assertions.assertEquals(3.0, gPerM2ToTonPerKm2.inverse().convert(3.0), 1e-10)
        Assertions.assertEquals(1e-10, gPerM2ToTonPerCm2.convert(1.0), 1e-20)
        Assertions.assertEquals(3e-10, gPerM2ToTonPerCm2.convert(3.0), 1e-20)
        Assertions.assertEquals(0.0, gPerM2ToTonPerCm2.offset())
        Assertions.assertEquals(1e-10, gPerM2ToTonPerCm2.scale(), 1e-10)
        Assertions.assertEquals(-0.0, gPerM2ToTonPerCm2.inverse().offset())
        Assertions.assertEquals(3.0, gPerM2ToTonPerCm2.inverse().convert(3e-10), 1e-10)
    }

    @Test
    fun temperatures() {
        val k: AbstractUnit = SimpleFundamentalUnit()
        val c: AbstractUnit = k.shift(273.15)
        val kToC: UnitConverter = k.getConverterTo(c)
        Assertions.assertEquals(-273.15, kToC.convert(0.0), 1e-10)
        Assertions.assertEquals(273.15, kToC.inverse().convert(0.0), 1e-10)

        // en combinaison avec d'autres unités, les conversions d'unités de températures doivent devenir linéaires
        val m: AbstractUnit = SimpleFundamentalUnit()
        val cPerM: AbstractUnit = SimpleDerivedUnit.of(c, m.factor(-1))
        val kPerM: AbstractUnit = SimpleDerivedUnit.of(k, m.factor(-1))
        val kPerMToCPerM: UnitConverter = kPerM.getConverterTo(cPerM)
        Assertions.assertEquals(3.0, kPerMToCPerM.convert(3.0), 1e-10)
        Assertions.assertEquals(3.0, kPerMToCPerM.inverse().convert(3.0), 1e-10)
    }

    @Test
    fun speed() {
        val m: AbstractUnit = SimpleFundamentalUnit()
        val km: AbstractUnit = m.scaleMultiply(1000.0)
        val s: AbstractUnit = SimpleFundamentalUnit()
        val h: AbstractUnit = s.scaleMultiply(3600.0)
        val ms: AbstractUnit = SimpleDerivedUnit.of(m, s.factor(-1))
        val kmh: AbstractUnit = SimpleDerivedUnit.of(km, h.factor(-1))
        val msToKmh: UnitConverter = ms.getConverterTo(kmh)
        Assertions.assertEquals(360.0, msToKmh.convert(100.0), 1e-10)
        Assertions.assertEquals(5.0, msToKmh.inverse().convert(18.0), 1e-10)
    }
}
