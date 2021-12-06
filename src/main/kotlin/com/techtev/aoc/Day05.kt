package com.techtev.aoc

fun main() {
    fun part1(input: List<String>) {
        val lines = parseInput(input).filter { it.start.x == it.end.x || it.start.y == it.end.y }

        val grid = createGrid(lines)

        fun drawLinesOnGrid(lines: List<Line>, grid: List<List<Int>>): List<List<Int>> {
            return lines.fold(grid) { gridAcc, line ->
                val xRange = if (line.start.x < line.end.x) line.start.x..line.end.x else line.start.x downTo line.end.x
                val yRange = if (line.start.y < line.end.y) line.start.y..line.end.y else line.start.y downTo line.end.y
                val mutableGrid = gridAcc.toMutableList()
                    .map { it.toMutableList() }
                if (xRange.first != xRange.last) {
                    xRange.forEach {
                        mutableGrid[line.start.y][it] = gridAcc[line.start.y][it] + 1
                    }
                } else {
                    yRange.forEach {
                        mutableGrid[it][line.start.x] = gridAcc[it][line.start.x] + 1
                    }
                }
                return@fold mutableGrid
            }
        }
        val dangerousPoints = getNumberOfDangerousPoints(drawLinesOnGrid(lines, grid))
        println(dangerousPoints)
    }
    fun part2(input: List<String>) {
        val lines = parseInput(input)
        val grid = createGrid(lines)
        fun drawLinesOnGrid(lines: List<Line>, grid: List<List<Int>>): List<List<Int>> {
            return lines.fold(grid) { gridAcc, line ->
                val xRange = if (line.start.x < line.end.x) line.start.x..line.end.x else line.start.x downTo line.end.x
                val yRange = if (line.start.y < line.end.y) line.start.y..line.end.y else line.start.y downTo line.end.y
                val mutableGrid = gridAcc.toMutableList()
                    .map { it.toMutableList() }
                val xIter = xRange.iterator()
                val yIter = yRange.iterator()
                while (xIter.hasNext() || yIter.hasNext()) {
                    val x = if (xIter.hasNext()) xIter.nextInt() else line.start.x
                    val y = if (yIter.hasNext()) yIter.nextInt() else line.start.y
                    mutableGrid[y][x] = mutableGrid[y][x] + 1
                }
                return@fold mutableGrid
            }
        }
        val dangerousPoints = getNumberOfDangerousPoints(drawLinesOnGrid(lines, grid))
        println(dangerousPoints)
    }
    val input = readInputLines("Day05")
    part1(input)
    part2(input)
}

private fun getNumberOfDangerousPoints(grid: List<List<Int>>): Int =
    grid.sumOf { it.sumOf { numberOfVents -> if (numberOfVents > 1) 1.toInt() else 0 } }

private fun createGrid(lines: List<Line>): List<List<Int>> {
    val maxX = lines.map { line ->
        maxOf(line.start.x, line.end.x)
    }.maxOf { it }
    val maxY = lines.map { line ->
        maxOf(line.start.y, line.end.y)
    }.maxOf { it }
    return List(maxY + 1) { List(maxX + 1) { 0 } }
}

private data class Point(val x: Int, val y: Int)

private data class Line(val start: Point, val end: Point)

private fun parseInput(input: List<String>): List<Line> {
    return input.map {
        val (x1, y1, x2, y2) = """(\d+),(\d+) -> (\d+),(\d+)""".toRegex().find(it)!!.destructured
        Line(Point(x1.toInt(), y1.toInt()), Point(x2.toInt(), y2.toInt()))
    }
}