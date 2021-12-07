package com.techtev.aoc

fun main() {
    fun part1(input: String) {
        fun increaseDays(fish: List<LanternFish>, numberOfDays: Int): List<LanternFish> {
            if (numberOfDays < 1) return fish
            val updatedFishList = buildList<LanternFish> {
                val mappedFish = fish.map {
                    val (updatedFish, newFish) = it.incrementDay()
                    if (newFish != null) this@buildList.add(newFish)
                    updatedFish
                }
                addAll(mappedFish)
            }
            return increaseDays(updatedFishList, numberOfDays - 1)
        }

        val initialLanternFish = getFish(input)

        println(increaseDays(initialLanternFish, 80).count())
    }

    fun part2(input: String) {
        fun increaseDays(fish: Map<Int, Long>, numberOfDays: Int): Long {
            if (numberOfDays < 1) return fish.values.sum()
            val updatedFishMap = buildMap<Int, Long> {
                fish.keys.sortedDescending().forEach { fishType ->
                    if (fishType == 0) {
                        put(8, fish[fishType] ?: 0)
                        put(6, (this[6] ?: 0) + (fish[fishType] ?: 0))
                    } else {
                        put(fishType - 1, fish[fishType] ?: 0)
                    }
                }
            }
            return increaseDays(updatedFishMap, numberOfDays - 1)
        }

        val initialLanternFish = getFish(input).groupBy {
            it.daysUntilBirth
        }.mapValues {
            it.value.count().toLong()
        }
        println(increaseDays(initialLanternFish, 256))
    }

    val input = readInputText("Day06")
    part1(input)
    part2(input)
}

data class LanternFish(val daysUntilBirth: Int) {
    fun incrementDay(): Pair<LanternFish, LanternFish?> {
        val updatedDaysUntilBirth = if (daysUntilBirth > 0) daysUntilBirth - 1 else 6
        val newLanternFish = if (daysUntilBirth > 0) null else LanternFish(8)
        return LanternFish(updatedDaysUntilBirth) to newLanternFish
    }
}

private fun getFish(input: String): List<LanternFish> =
    input.filter { it != '\n' }.split(',').map { LanternFish(it.toInt()) }