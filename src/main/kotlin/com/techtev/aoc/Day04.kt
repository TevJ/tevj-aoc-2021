package com.techtev.aoc

typealias Board = List<List<Pair<Int, Boolean>>>

fun main() {
    fun part1(input: List<String>) {
        val (numbers, boards) = parseNumbersAndBoards(input)
        fun findWinner(numbers: List<Int>, boards: List<Board>): Pair<Int, Board> {
            if (numbers.isEmpty()) {
                println(boards)
                throw IllegalStateException("No winning boards")
            }
            val updatedBoards = markNumberOnBoards(numbers.first(), boards)
            updatedBoards.forEach {
                if (it.isWinning()) {
                    return numbers.first() to it
                }
            }
            return findWinner(numbers.drop(1), updatedBoards)
        }
        val (winningNumber, winningBoard) = findWinner(numbers, boards)
        val winingBoardTotal = winningBoard.flatMap { row -> row.filter { !it.second }.map { it.first } }.sum()
        println(winningNumber * winingBoardTotal)
    }

    fun part2(input: List<String>) {
        val (numbers, boards) = parseNumbersAndBoards(input)
        fun findLastWinner(
            numbers: List<Int>,
            boards: List<Board>,
            latestWinner: Board?,
            latestWinningNumber: Int?
        ): Pair<Int, Board> {
            if (numbers.isEmpty()) {
                checkNotNull(latestWinner)
                checkNotNull(latestWinningNumber)
                return latestWinningNumber to latestWinner
            }
            var winningBoard: Board? = latestWinner
            var winningNumber: Int? = latestWinningNumber
            val updatedBoards = markNumberOnBoards(numbers.first(), boards)
                .filter { board ->
                    !board.isWinning().also { isWinning ->
                        if (isWinning) {
                            winningBoard = board
                            winningNumber = numbers.first()
                        }
                    }
                }
            return findLastWinner(numbers.drop(1), updatedBoards, winningBoard, winningNumber)
        }
        val (lastWinningNumber, lastWinningBoard) = findLastWinner(numbers, boards, null, null)
        val lastWiningBoardTotal = lastWinningBoard.flatMap { row -> row.filter { !it.second }.map { it.first } }.sum()
        println(lastWinningNumber * lastWiningBoardTotal)
    }

    val input = readInputLines("Day04")
    part1(input)
    part2(input)
}

fun Board.isWinning(): Boolean {

    fun Board.hasWinningRow(): Boolean {
        forEach { row ->
            if (row.all { it.second }) return true
        }
        return false
    }

    fun Board.hasWinningColumn(): Boolean {
        (0..lastIndex).forEach { i ->
            val column = buildList { this@hasWinningColumn.forEach { this@buildList.add(it[i]) } }
            if (column.all { it.second }) return true
        }
        return false
    }
    return this.hasWinningRow() || this.hasWinningColumn()
}

fun markNumberOnBoards(number: Int, boards: List<Board>): List<Board> {
    return boards.map { board ->
        board.map { row ->
            row.map { (n, isMarked) ->
                n to ((n == number) || isMarked)
            }
        }
    }
}

fun parseNumbersAndBoards(input: List<String>): Pair<List<Int>, List<Board>> {
    val numbers = input[0].split(",").map { it.toInt() }
    val boards: List<List<List<Pair<Int, Boolean>>>> = buildList {
        input.drop(2).map {
            if (it == "\n") return@map emptyList<Pair<Int, Boolean>>()
            it.trim().split(" ").filter { it.isNotEmpty() }.map { it.toInt() to false }
        }.filter { it.isNotEmpty() }.fold<List<Pair<Int, Boolean>>, Board>(emptyList()) { acc, list ->
            if (acc.size == 4) {
                this@buildList.add(acc.toMutableList().apply { add(list) })
                return@fold emptyList()
            }
            acc.toMutableList().apply { add(list) }
        }
    }
    return numbers to boards
}