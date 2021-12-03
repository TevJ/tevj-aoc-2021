package com.techtev.aoc

fun main() {
    fun part1(input: List<String>) {
        val gammaRate = getRotatedList(input)
            .map { it.map { c -> c.toString().toInt() }.sorted()[it.size / 2].toString() }
            .reduce { acc, s -> "$acc$s" }
            .toInt(2)

        val epsilonRate = gammaRate.inv() and (1 shl input.first().length) - 1

        println(gammaRate * epsilonRate)
    }

    fun part2(input: List<String>) {
        fun findOxygenRating(index: Int, input: List<String>): Int {
            if (input.size == 1) return input.first().toInt(2)
            val chars = getRotatedList(input)[index]
            val mostCommonChar = chars.map { c -> c.toString().toInt() }.sorted()[chars.size / 2].toString()
            val filteredInput = input.filter {
                it.toCharArray()[index] == mostCommonChar.first()
            }
            return findOxygenRating(index + 1, filteredInput)
        }

        fun findCo2ScrubberRating(index: Int, input: List<String>): Int {
            if (input.size == 1) return input.first().toInt(2)
            val chars = getRotatedList(input)[index]
            val charsSum = chars.sumOf { it.toString().toInt() }
            val leastCommonChar = if (charsSum.toFloat() >= input.size.toFloat() / 2) '0' else '1'
            val filteredInput = input.filter {
                it.toCharArray()[index] == leastCommonChar
            }
            return findCo2ScrubberRating(index + 1, filteredInput)
        }
        val lifeSupportRating = findOxygenRating(0, input) * findCo2ScrubberRating(0, input)
        println(lifeSupportRating)
    }

    val input = readInputLines("Day03")
    part1(input)
    part2(input)
}

fun getRotatedList(input: List<String>): List<List<Char>> = input.map { binaryString ->
    binaryString.toCharArray().mapIndexed { index, c ->
        index to c
    }
        .toMap()
}
    .asSequence()
    .flatMap {
        it.asSequence()
    }.groupBy({ it.key }, { it.value })
    .map { it.value }