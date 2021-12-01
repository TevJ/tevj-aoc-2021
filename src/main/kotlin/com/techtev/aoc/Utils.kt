package com.techtev.aoc

import java.io.File

fun readInputLines(name: String): List<String> =
    File("src/main/kotlin/com/techtev/aoc", "$name.txt").readLines()