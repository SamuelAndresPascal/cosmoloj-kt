package com.cosmoloj.kt.learning.enum

enum class Toto0(val label: String, val ref: Tata0) {

    VAL1("ONE", Tata0.ONE),
    VAL2("TWO", Tata0.TWO);

    init {
        println("create0 " + name);
    }
}

enum class Tata0(val label: String, val ref: Toto0) {
    ONE("one", Toto0.VAL1),
    TWO("two", Toto0.VAL1),
    THREE("three", Toto0.VAL2);

    init {
        println("create0 " + name);
    }
}

enum class Toto1(val label: String, val ref: Tata1) {

    VAL1("ONE", Tata1.ONE),
    VAL2("TWO", Tata1.TWO);

    init {
        println("create1 " + name);
    }
}

enum class Tata1(val label: String, val ref: Toto1) {
    ONE("one", Toto1.VAL1),
    TWO("two", Toto1.VAL1),
    THREE("three", Toto1.VAL2);

    init {
        println("create1 " + name);
    }
}

enum class Toto2(val label: String, val ref: Tata2, val vals: List<Tata2>) {

    VAL1("ONE", Tata2.ONE, Tata2.val1()),
    VAL2("TWO", Tata2.TWO, Tata2.val2());

    init {
        println("create2 " + name);
    }

    companion object {
        init {
            println("create compagnon toto2 ");
        }

        fun one() = entries.filter {
            val tata = Tata2.ONE
            println("tata vaut " + tata + " (" + it.ref + ")")
            it.ref == tata
        }
        fun two() = entries.filter {
            val tata = Tata2.TWO
            println("tata vaut " + tata + " (" + it.ref + ")")
            it.ref == tata
        }
    }
}

enum class Tata2(val label: String, val ref: Toto2) {
    ONE("one", Toto2.VAL1),
    TWO("two", Toto2.VAL1),
    THREE("three", Toto2.VAL2);

    init {
        println("create2 " + name);
    }

    companion object {

        init {
            println("create compagnon tata2 ");
        }

        fun val1() = entries.filter {
            val ref = Toto2.VAL1
            println("que vaut la ref dans val1 ? " + ref)
            it.ref == ref
        }

        fun val2() = entries.filter {
            val ref = Toto2.VAL2
            println("que vaut la ref dans val2 ? " + ref)
            it.ref == ref
        }
    }
}

fun main() {

    // on va déclencher le chargement des instances de Toto1 et donc de Tata1 au préalable pour passer au constructeur
    println(Toto1.VAL1)
    println(Toto1.VAL1.ref)
    // Toto1.VAL1 n'existe pas encore à l'instancitation de Tata1.ONE
    println(Toto1.VAL1.ref.ref)
    println(Toto1.VAL2)
    println(Toto1.VAL2.ref)
    // Toto1.VAL1 n'existe pas encore à l'instancitation de Tata1.TWO car l'appel d'une instance déclenche le chargement
    // de toutes les instances de l'enum
    println(Toto1.VAL2.ref.ref)

    // on n'a pas encore chargé Toto0 ni Tata0
    // cette fois on commencer par Tata:
    // donc pour créer les intances de Tata0, ce sont les instances de Toto0 qui seront créées d'abord car nécessaires
    println(Tata0.ONE)
    println(Tata0.ONE.ref)
    println(Tata0.ONE.ref.ref)

    // necessaire pour instancier l'objet compagnon dans Tata2 et éviter une exception plus bas !!
    // ceci déclanche l'instanciation des Toto2, donc des Tata2 au préalable, suivies de leur objet compagnon
    // si on commence par charger les Tata2, on va commencer par instancier au préalable les Toto2 sans
    // aller jusqu'au compagnon de Tata2 qui sera pourtant nécessaire aux instances de Tata1
    println(Toto2.VAL1)
    // ici tout marche bien car la valeur de référence dans filter n'est pas null
    println(Tata2.val1())
    println(Tata2.val2())
    println(Toto2.one())
    println(Toto2.two())
    // à ce stade, on a tous les éléments dans la liste alors qu'ils auraient dû être filtrés
    println(Toto2.VAL1.vals)
    println(Toto2.VAL2.vals)
}
