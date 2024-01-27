package com.cosmoloj.kt.learning.file

import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter

private const val THRESHOLD = 1000000

fun main() {

    val file1 = File("titi.txt")


    // INCORRECT : en passant par la méthode d'extension à l'intérieur d'une boucle'
    var start = System.currentTimeMillis()
    repeat (THRESHOLD) {
        file1.appendText("toto")
    }
    println(System.currentTimeMillis() - start)
    // éléments | temps (ms)
    // 10 | 6
    // 100 | 10
    // 1000 | 50
    // 10000 | 145
    // 100000 | 1208
    // 1000000 | 8000
    println(file1.delete())


    val file2 = File("titi.txt")
    // CORRECT : en incluant la boucle dans un writer mis en tampon et sécurisé par un try/catch
    start = System.currentTimeMillis()
    BufferedWriter(FileWriter(file2)).use { bw ->
        repeat (THRESHOLD) {
            bw.write("toto")
        }
    }
    println(System.currentTimeMillis() - start)
    // éléments | temps (ms)
    // 10 | 1
    // 100 | 1
    // 1000 | 5
    // 10000 | 9
    // 100000 | 15
    // 1000000 | 50
    println(file2.delete())
}
