package com.cosmoloj.kt.learning.dataclass


data class Mutable(var id: Int)

data class Immutable(val id: Int)


fun main() {

    val muable = Mutable(0)

    val set: MutableSet<Mutable> = mutableSetOf(muable)


    // vrai, bien évidemment
    println(set.contains(muable))


    // un élément dans l'ensemble
    println(set.size)


    // retrait de l'élément
    println(set.remove(muable))


    // l'ensemble est vide
    println(set.size)


    // on ajoute de nouveau l'élément dans l'ensemble
    set.add(muable)


    // un élément dans l'ensemble
    println(set.size)


    // changement d'état du muable impactant le calcul de hashcode
    muable.id = 2


    // on ne retrouve pas l'élément dans l'ensemble alors que l'instance y est bien dedans !!!!
    println(set.contains(muable))


    // tentative de retrait de l'élément après son changement : échec car le hashcode a changé depuis l'insertion
    // il aurait fallu 1) sortir l'objet du set 2) le modifier 3) le réinsérer (beurk !)
    println(set.remove(muable))


    // l'ensemble fait toujours une taille de 1
    println(set.size)
}