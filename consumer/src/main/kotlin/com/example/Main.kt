package com.example

fun main() {
    val resourceContent =
        object {}.javaClass.getResourceAsStream("/zipmeup.txt")?.bufferedReader()?.use { it.readText() }
            ?: throw IllegalArgumentException("zipmeup not found")

    println(resourceContent)
}
