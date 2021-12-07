package com.techtev.aoc

import kotlin.math.abs

fun main() {
    fun part1(input: List<String>) {
        val crabPositions = parseInput(input)
        fun getCheapestCrabAlignment(
            crabPositions: List<Int>,
            calculatedCosts: List<Int>,
            remainingCosts: List<Int>
        ): Int {
            if (remainingCosts.isEmpty()) return calculatedCosts.minOrNull() ?: 0
            val currentPosition = calculatedCosts.size
            val cost = crabPositions.sumOf {
                abs(it - currentPosition)
            }
            return getCheapestCrabAlignment(crabPositions, calculatedCosts.plus(cost), remainingCosts.drop(1))
        }
        println(getCheapestCrabAlignment(crabPositions, emptyList(), List(crabPositions.size) { 0 }))
    }

    fun part2(input: List<String>) {
        val crabPositions = parseInput(input)
        fun getCheapestCrabAlignment(
            crabPositions: List<Int>,
            calculatedCosts: List<Int>,
            remainingCosts: List<Int>
        ): Int {
            if (remainingCosts.isEmpty()) return calculatedCosts.minOrNull() ?: 0
            val currentPosition = calculatedCosts.size
            val cost = crabPositions.sumOf {
                val differenceMagnitude = abs(it - currentPosition)
                ((1 + differenceMagnitude) * differenceMagnitude) / 2
            }
            return getCheapestCrabAlignment(crabPositions, calculatedCosts.plus(cost), remainingCosts.drop(1))
        }
        println(getCheapestCrabAlignment(crabPositions, emptyList(), List(crabPositions.size) { 0 }))
    }

    val input = readInputLines("Day07")
    part1(input)
    part2(input)
}

private fun parseInput(input: List<String>): List<Int> {
    return input.map { line ->
        line.split(',').map { it.toInt() }
    }.flatten()
}