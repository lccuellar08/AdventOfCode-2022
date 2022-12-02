package adventofcode

import java.io.File
import java.lang.IllegalArgumentException

fun mainDay2(currentPath: String) {
    val filePath = "app/src/main/resources"
    val fileName = "input2.txt"

//    val moves = readInputWrong("${currentPath}/${filePath}/${fileName}")
    val moves = readInput("${currentPath}/${filePath}/${fileName}")
//    moves.forEach {
//        println("${it.first} - ${it.second}: ${calculatePoints(it.first, it.second)}")
//    }

    val totalPoints = moves.map { calculatePoints(it.first, it.second) }.sum()
    println("Total points: ${totalPoints}")
}

private fun calculatePoints(move1: Move, move2: Move): Int {
    var points = 0

    when(move1) {
        Move.ROCK -> when(move2) {
            Move.ROCK -> points += 3 // Player 2 Draws
            Move.PAPER -> points += 6 // Player 2 Wins
            Move.SCISSORS -> points = 0 // Player 2 Loses
        }
        Move.PAPER -> when(move2) {
            Move.ROCK -> points = 0 // Player 2 Loses
            Move.PAPER -> points += 3 // Player 2 Draws
            Move.SCISSORS -> points += 6 // Player 2 Wins
        }
        Move.SCISSORS -> when(move2) {
            Move.ROCK -> points += 6 // Player 2 Wins
            Move.PAPER -> points = 0 // Player 2 Loses
            Move.SCISSORS -> points += 3 // Player 2 Draws
        }
    }

    return points + move2.value
}

private enum class Move(val value: Int){
    ROCK(1),
    PAPER(2),
    SCISSORS(3)
}

private fun readInput(fileName: String): List<Pair<Move,Move>> {
    var movesInRound = mutableListOf<Pair<Move,Move>>()

    File(fileName).forEachLine {
        val moves = it.split(" ")
        val move1 = when(moves[0]) {
            "A" -> Move.ROCK
            "B" -> Move.PAPER
            "C" -> Move.SCISSORS
            else -> throw IllegalArgumentException("Wrong input for player 1: ${moves[0]}")
        }
        val move2 = when(moves[1]) {
            "X" -> when(move1) { // Must Lose
                Move.ROCK -> Move.SCISSORS
                Move.PAPER -> Move.ROCK
                Move.SCISSORS -> Move.PAPER
            }
            "Y" -> move1 // Must Draw
            "Z" -> when(move1) { // Must Win
                Move.ROCK -> Move.PAPER
                Move.PAPER -> Move.SCISSORS
                Move.SCISSORS -> Move.ROCK
            }
            else -> throw IllegalArgumentException("Wrong input for player 2: ${moves[1]}")
        }
        movesInRound.add(Pair(move1, move2))
    }

    return movesInRound
}

private fun readInputWrong(fileName: String): List<Pair<Move,Move>> {
    var movesInRound = mutableListOf<Pair<Move,Move>>()

    File(fileName).forEachLine {
        val moves = it.split(" ")
        val move1 = when(moves[0]) {
            "A" -> Move.ROCK
            "B" -> Move.PAPER
            "C" -> Move.SCISSORS
            else -> throw IllegalArgumentException("Wrong input for player 1: ${moves[0]}")
        }
        val move2 = when(moves[1]) {
            "X" -> Move.ROCK
            "Y" -> Move.PAPER
            "Z" -> Move.SCISSORS
            else -> throw IllegalArgumentException("Wrong input for player 2: ${moves[1]}")
        }
        movesInRound.add(Pair(move1, move2))
    }

    return movesInRound
}