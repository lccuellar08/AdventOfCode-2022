package adventofcode

import java.io.File

fun mainDay5(currentPath: String) {
    val filePath = "app/src/main/resources"
    val fileName = "input5.txt"

    val (listOfBoxes, listOfMoves) = readInput("${currentPath}/${filePath}/${fileName}")
//    val newBoxes = processMoves(listOfBoxes, listOfMoves)
    val newBoxes = processMoves9001(listOfBoxes, listOfMoves)
    var topStacks = ""
    newBoxes.forEach {
        topStacks += it.boxes.first()
    }

    println("Top stack: $topStacks")
}

private fun processMoves9001(listOfBoxes: List<BoxStack>, listOfMoves: List<CraneMove>): List<BoxStack> {
    listOfMoves.forEach {
        for(i in 1..it.amount) {
            val fromRemoved = listOfBoxes[it.from - 1].boxes.removeAt(0)
            listOfBoxes[it.to - 1].boxes.add(i - 1, fromRemoved)
        }
    }

    return listOfBoxes

}

private fun processMoves(listOfBoxes: List<BoxStack>, listOfMoves: List<CraneMove>): List<BoxStack> {
    listOfMoves.forEach {
        for(i in 1..it.amount) {
            val fromRemoved = listOfBoxes[it.from - 1].boxes.removeAt(0)
            listOfBoxes[it.to - 1].boxes.add(0, fromRemoved)
        }
    }

    return listOfBoxes
}

private data class CraneMove(val from: Int, val to: Int, val amount: Int)
private data class BoxStack(val boxes: MutableList<Char>)

private fun readInput(fileName: String): Pair<List<BoxStack>, List<CraneMove>> {
    val listOfBoxStrings = mutableListOf<String>()
    val listOfMoves = mutableListOf<CraneMove>()
    var numOfBoxes = 0

    File(fileName).forEachLine {
        if(it.contains("move")) {
            val tokens = it.split(" ")
            val craneMove = CraneMove(tokens[3].toInt(), tokens[5].toInt(), tokens[1].toInt())
            listOfMoves.add(craneMove)
        } else if(it.contains("[")) {
            listOfBoxStrings.add(it)
        } else if(it.isNotBlank()){
            numOfBoxes = it.trim().last().digitToInt()
        }
    }

    val listOfBoxes = mutableListOf<BoxStack>()
    for(i in 0..numOfBoxes - 1) {
        listOfBoxes.add(BoxStack(mutableListOf()))
    }

    val offSet = 1

    listOfBoxStrings.forEach{
        for(i in offSet..it.length step 4) {
            if(!it[i].isWhitespace()) {
                listOfBoxes[(i - offSet) / 4].boxes.add(it[i])
            }
        }
    }

    return Pair(listOfBoxes, listOfMoves)
}