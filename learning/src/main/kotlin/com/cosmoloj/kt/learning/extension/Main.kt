package com.cosmoloj.kt.learning.extension


/**
 * Illustration de la méfiance à garder à l'égard des méthodes d'extension Kotlin dont le sucre syntaxique peut coûter
 * cher !
 *
 * Le pattern immutable est très précieux mais il ne faut pas en abuser. Son utilisation judicieuse est équilibrée par
 * le pattern builder.
 */
fun main() {

    val threshold : Int = 100000
    val listToAdd = listOf(1)

    // CORRECT : en passant par une liste mutable : pattern builder
    var start = System.currentTimeMillis()
    val list1 = mutableListOf<Int>()
    for (i in 0..threshold) {
        list1.addAll(listToAdd)
    }
    // la manière correcte de procéder est de produire liste immutable après remplissage de la liste mutable (builder)
    println(list1.toList().size)
    println(System.currentTimeMillis() - start) // 100000 éléments : 12ms / 1 million : 52ms


    // INCORRECT : on peut croire bien faire en utilisant une liste immutable… mais sa référence est mutable
    start = System.currentTimeMillis()
    var list2 = listOf<Int>()
    for (i in 0..threshold) {
        // ici on va créer une nouvelle liste pour renvoyer le résultat ! Très cher en allocation et en GC !
        list2 = list2.plus(listToAdd)
    }
    println(list2.size) // on n'a travaillé qu'avec des objets immutables mais au prix d'une référence mutable
    println(System.currentTimeMillis() - start) // 100000 éléments : 3600ms / 1 million : les minutes passent…
}