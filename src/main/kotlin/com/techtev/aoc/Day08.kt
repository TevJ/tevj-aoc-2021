package com.techtev.aoc

fun main() {
    fun part1(input: List<String>) {
        val displayEntries = parseInput(input)
        val uniqueSegments = displayEntries.map {
            it.output
        }.sumOf { outputs ->
            outputs.count { it.length in listOf(2, 4, 3, 7) }
        }
        println(uniqueSegments)
    }

    fun part2(input: List<String>) {
        val displayEntries = parseInput(input).map {
            DisplayEntry(
                it.signals.map { it.toList().sorted().joinToString(separator = "") },
                it.output.map { it.toList().sorted().joinToString(separator = "") })
        }
        val outputTotal = displayEntries.sumOf { displayEntry ->
            displayEntry.output.map { displayEntry.toSegmentMapping()[it] }.joinToString("").toInt()
        }
        println(outputTotal)
    }
    
    val input = readInputLines("Day08")
    part1(input)
    part2(input)
}

private fun DisplayEntry.toSegmentMapping(): Map<String, Int> {
    return buildMap<Int, String> {
        signals.forEach {
            when (it.length) {
                2 -> this[1] = it
                4 -> this[4] = it
                3 -> this[7] = it
                7 -> this[8] = it
            }
        }
        this[3] = signals.single {
            it.length == 5 && it.toList().containsAll(this[1]?.toList()!!)
        }
        this[9] = signals.single {
            it.length == 6 && it.toList().containsAll(this[3]?.toList()!!)
        }
        this[0] = signals.single {
            it.length == 6 && it != this[9] && it.toList().containsAll(this[1]?.toList()!!)
        }
        this[6] = signals.single {
            it.length == 6 && !this.values.contains(it)
        }
        this[2] = signals.single {
            it.length == 5 && it.contains(this[8]?.toList()?.subtract(this[9]?.toList()!!)!!.joinToString(""))
        }
        this[5] = signals.single {
            !this.values.contains(it)
        }
    }.entries.associateBy({ it.value }) { it.key }
}

data class DisplayEntry(val signals: List<String>, val output: List<String>)

private fun parseInput(input: List<String>): List<DisplayEntry> {
    return input.map { inputLine ->
        inputLine.split('|').map { it.trim() }.map { it.split(' ') }
            .run {
                DisplayEntry(first(), this[1])
            }
    }
}
