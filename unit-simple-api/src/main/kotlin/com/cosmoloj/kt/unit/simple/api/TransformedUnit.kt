package com.cosmoloj.kt.unit.simple.api

interface TransformedUnit : AbstractUnit {

    fun reference(): AbstractUnit

    fun toReference(): UnitConverter
}