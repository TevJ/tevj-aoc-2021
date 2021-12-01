package com.techtev.aoc

import java.io.File

fun main() {
    val input = readInputLines("Day01")

    val depthReadings = input.map { it.toInt() }

    fun List<Int>.calculateIncrements(): Int = foldIndexed(0) { index: Int, acc: Int, reading: Int  ->
        if (index == 0) return@foldIndexed acc
        if (reading > this[index - 1]) acc + 1 else acc
    }

    val numberOfIncrements = depthReadings.calculateIncrements()
    println("Number of increments in depth readings: $numberOfIncrements")

    val slidingIncrements = depthReadings.windowed(3) { window ->
        window.sum()
    }.calculateIncrements()
    println("Number of increments in depth readings with sliding window: $slidingIncrements")
}