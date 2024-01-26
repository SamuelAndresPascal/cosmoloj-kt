package com.cosmoloj.kt.learning.array


/**
 * Principe de programmation : prendre garde à l'organisation de la mémoire dans le parcours des tableaux
 * multidimentionnels.
 *
 * Tout tableau multidimentionnel est arrangé en mémoire comme un tableau de tableaux et en dernière instance comme un
 * simple tableau d'une seule dimension.
 *
 * Autant que possible il faut essayer de parcourir les éléments du tableau multidimentionnel dans l'ordre des éléments
 * du tableau à une seule dimension en mémoire pour éviter les allers-retours qui constituent une perte de temps bien
 * plus importante que les accès en lecture/écriture eux-mêmes.
 *
 * La convention en Java est contraire à la convention en C/C++.
 *
 * @author Samuel Andrés
 */
fun main() {

    val size = 10000
    val array = Array(size) { DoubleArray(size) }

    // les indices doivent être parcourus dans des boucles imbriquées dans l'ordre des dimensions (contraire du C)
    var start = System.currentTimeMillis()
    for (i in 0 ..<size) {
        for (j in 0 ..<size) {
            array[i][j]++
        }
    }
    println(System.currentTimeMillis() - start) // 75ms pour 10000x10000 éléments

    // sinon le temps de parcours s'allonge de manière très importante
    start = System.currentTimeMillis()
    for (i in 0..<size) {
        for (j in 0 ..<size) {
            array[j][i]++
        }
    }
    println(System.currentTimeMillis() - start) // 1593ms pour 10000x10000 éléments
}