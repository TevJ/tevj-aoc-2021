package com.techtev.aoc

fun main() {
    val input = readInputLines("Day02")
    val commands: List<Command> = input.map {
        val splitInput = it.split(" ")
        Command(Direction.valueOf(splitInput[0].uppercase()), splitInput[1].toInt())
    }

    fun part1() {
        fun Position.applyCommands(commands: List<Command>): Position =
            commands.fold(this) { acc, command ->
                when (command.direction) {
                    Direction.FORWARD -> Position(acc.horizontal + command.magnitude, acc.depth)
                    Direction.DOWN -> Position(acc.horizontal, acc.depth + command.magnitude)
                    Direction.UP -> Position(acc.horizontal, acc.depth - command.magnitude)
                }
            }

        val finalPosition = Position(0, 0).applyCommands(commands)
        println("Final position: $finalPosition")
        println("Multiplied: ${finalPosition.horizontal * finalPosition.depth}")
    }

    fun part2() {
        fun PositionAndAim.applyCommands(commands: List<Command>): PositionAndAim =
            commands.fold(this) { acc, command ->
                when (command.direction) {
                    Direction.FORWARD -> PositionAndAim(
                        horizontal = acc.horizontal + command.magnitude,
                        depth = acc.depth + (command.magnitude * acc.aim),
                        aim = acc.aim
                    )
                    Direction.DOWN -> PositionAndAim(
                        horizontal = acc.horizontal,
                        depth = acc.depth,
                        aim = acc.aim + command.magnitude
                    )
                    Direction.UP -> PositionAndAim(
                        horizontal = acc.horizontal,
                        depth = acc.depth,
                        aim = acc.aim - command.magnitude
                    )
                }
            }


        val finalPosition = PositionAndAim(0, 0, 0).applyCommands(commands)
        println("Final position: $finalPosition")
        println("Multiplied: ${finalPosition.horizontal * finalPosition.depth}")
    }

    part1()
    part2()
}

data class Position(val horizontal: Int, val depth: Int)

data class Command(val direction: Direction, val magnitude: Int)

enum class Direction {
    FORWARD, DOWN, UP
}

data class PositionAndAim(val horizontal: Int, val depth: Int, val aim: Int)