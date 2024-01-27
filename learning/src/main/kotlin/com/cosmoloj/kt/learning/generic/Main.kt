package com.cosmoloj.kt.learning.generic

private const val DOUBLE_TO_ADD: Double = 0.2

/**
 * Illustration de l'insécurité des types génériques mal employés.
 */
fun main() {

    val list : MutableList<Int> = mutableListOf()

    // erreur à la compilation si j'essaye d'ajouter un élément qui n'est pas du type générique Int
    // list.add(0.2)

    // en faisant un cast sur la liste, on peut tromper le compilateur qui lève une alerte unchecked cast
    // mais la compilation passe et le type générique étant oublié à l'exécution, rien n'empêche désormais d'ajouter
    // un élément qui n'est pas du type générique Int !
    (list as MutableList<Any>).add(DOUBLE_TO_ADD)

    // Noter que le compilateur ne dit rien sur un cas correct car du point de vue du type on a bien
    // MutableList<Int> <: Collection<Int>
    // En revanche MutableList<Int> ne spécialise pas MutableList<Any> qui sont deux types sans relation hiérarchique
    // dont le seul point commun est d'être représenté de manière identique à l'exécution
    print((list as Collection<Int>).size)


    println(list)
    println(list[0])
    println(list::class)
    println(list[0]::class == Double::class) // on a bien un élément de type double dans une liste de Int !!!
}
