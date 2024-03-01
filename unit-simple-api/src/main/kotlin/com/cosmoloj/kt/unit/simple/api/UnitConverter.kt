package com.cosmoloj.kt.unit.simple.api

interface UnitConverter {

    fun scale(): Double

    fun offset(): Double

    fun inverse(): UnitConverter

    fun linear(): UnitConverter

    fun linearPow(pow: Double): UnitConverter

    fun convert(value: Double): Double

    fun concatenate(unitConverter: UnitConverter): UnitConverter

    operator fun not(): UnitConverter = inverse()
}
