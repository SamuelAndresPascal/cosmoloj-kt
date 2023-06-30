package com.cosmoloj.kt.unit.simple.api

interface DerivedUnit : AbstractUnit {

    fun definition(): List<Factor>
}