package com.techtev.aoc

import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.max

fun main() {
    fun part1(input: List<String>) {
        fun getLowestPoints(heightMap: List<List<Int>>): List<Int> {
            return buildList {
                heightMap.indices.forEach { y ->
                    heightMap[y].indices.forEach { x ->
                        val currentPoint = heightMap[y][x]
                        val adjacentValues = listOfNotNull(
                            heightMap.getOrNull(y)?.getOrNull(x - 1),
                            heightMap.getOrNull(y)?.getOrNull(x + 1),
                            heightMap.getOrNull(y - 1)?.getOrNull(x),
                            heightMap.getOrNull(y + 1)?.getOrNull(x)
                        )
                        if (currentPoint < adjacentValues.minOrNull()!!) add(currentPoint)
                    }
                }
            }
        }
        val heightMap = parseInput(input)
        println(getLowestPoints(heightMap).sumOf { it + 1 })
    }

    fun part2(input: List<String>) {
        fun getLowestPoints(heightMap: List<List<Int>>): List<MapPoint> {
            return buildList {
                heightMap.indices.forEach { y ->
                    heightMap[y].indices.forEach { x ->
                        val currentPoint = heightMap[y][x]
                        val adjacentValues = listOfNotNull(
                            heightMap.getOrNull(y)?.getOrNull(x - 1),
                            heightMap.getOrNull(y)?.getOrNull(x + 1),
                            heightMap.getOrNull(y - 1)?.getOrNull(x),
                            heightMap.getOrNull(y + 1)?.getOrNull(x)
                        )
                        if (currentPoint < adjacentValues.minOrNull()!!) add(MapPoint(x, y))
                    }
                }
            }
        }
        fun getBasinSizes(heightMap: List<List<Int>>): List<Int> {
            val lowestPoints = getLowestPoints(heightMap)
            fun getBasinSizeStack(startingPoint: MapPoint): List<MapPoint> {
                return buildList {
                    val toCheck: Queue<MapPoint> = ArrayDeque()
                    toCheck.add(startingPoint)
                    while (toCheck.isNotEmpty()) {
                        val currentPoint = toCheck.remove()
                        if (currentPoint.y in heightMap.indices
                            && currentPoint.x in heightMap[currentPoint.y].indices
                            && heightMap[currentPoint.y][currentPoint.x] < 9
                            && !this.contains(currentPoint)
                        ) {
                            add(currentPoint)
                            if (!toCheck.contains(currentPoint.copy(x = currentPoint.x - 1))) toCheck.add(
                                currentPoint.copy(
                                    x = currentPoint.x - 1
                                )
                            )
                            if (!toCheck.contains(currentPoint.copy(x = currentPoint.x + 1))) toCheck.add(
                                currentPoint.copy(
                                    x = currentPoint.x + 1
                                )
                            )
                            if (!toCheck.contains(currentPoint.copy(y = currentPoint.y - 1))) toCheck.add(
                                currentPoint.copy(
                                    y = currentPoint.y - 1
                                )
                            )
                            if (!toCheck.contains(currentPoint.copy(y = currentPoint.y + 1))) toCheck.add(
                                currentPoint.copy(
                                    y = currentPoint.y + 1
                                )
                            )
                        }
                    }
                }
            }
            return lowestPoints.map {
                getBasinSizeStack(it).size
            }
        }
        val heightMap = parseInput(input)
        val basinSizes = getBasinSizes(heightMap)
        println(getBasinSizes(heightMap).sortedDescending().take(3).reduce { acc, i -> acc * i })
    }

    val input = readInputLines("Day09")
    part1(input)
    part2(input)
}

data class MapPoint(val x: Int, val y: Int)

private fun parseInput(input: List<String>): List<List<Int>> {
    return input.map { line ->
         line.map { it.toString().toInt() }
    }
}