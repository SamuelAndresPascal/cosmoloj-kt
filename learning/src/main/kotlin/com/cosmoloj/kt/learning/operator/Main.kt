package com.cosmoloj.kt.learning.operator

private const val DOUBLE_TO_ADD: Double = 0.2

/**
 * Illustration des coupes-circuits sur les opérateurs logiques
 */
fun main() {

    fun titi(): Boolean {
        println("titi")
        return true
    }

    fun toto(): Boolean {
        println("toto")
        return false
    }

    toto() && titi() // l'évaluation de toto suffit

    titi() && toto() // on évalue titi (d'abord) et toto (ensuite)

    titi() || toto() // l'évaluation de titi suffit

    toto() || titi() // on évalue toto (d'abord) et toto (ensuite)
}
