package com.cosmoloj.kt.unit.simple.api

interface Factor {

    fun dim(): AbstractUnit

    fun numerator(): Int

    fun denominator(): Int

    fun power(): Double = if (denominator() == 1) numerator().toDouble() else numerator().toDouble() / denominator()

    operator fun times(value: Any): AbstractUnit

    operator fun div(value: Any): AbstractUnit
}
