package adventofcode

import java.io.File

fun mainDay4(currentPath: String) {
    val filePath = "app/src/main/resources"
    val fileName = "input4.txt"

    val pairsList = readInput("${currentPath}/${filePath}/${fileName}")

    val containingPairs = findContainingPairs(pairsList)
    val totalContainingPairs = containingPairs.count()
    println("Total containing pairs: $totalContainingPairs")

    val overlappingPairs = findOverlappingPairs(pairsList)
    val totalOverlappingPairs = overlappingPairs.count()
    println("Total overlapping pairs: $totalOverlappingPairs")
}

fun findOverlappingPairs(pairslist: List<Pair<Pair<Int,Int>, Pair<Int,Int>>>): List<Pair<Pair<Int,Int>, Pair<Int,Int>>> {
    var listOfOverlapingPairs = mutableListOf<Pair<Pair<Int,Int>, Pair<Int,Int>>>()

    pairslist.forEach {
        val pair1  = it.first
        val pair2 = it.second

        if(pair1.second - pair1.first > pair2.second - pair2.first) {
            if((pair2.first >= pair1.first && pair2.first <= pair1.second) ||
               (pair2.second >= pair1.first && pair2.second <= pair1.second)) {
                listOfOverlapingPairs.add(Pair(pair1, pair2))
            }
        } else {
            if((pair1.first >= pair2.first && pair1.first <= pair2.second) ||
               (pair1.second >= pair2.first && pair1.second <= pair2.second)) {
                listOfOverlapingPairs.add(Pair(pair1, pair2))
            }
        }
    }

    return listOfOverlapingPairs
}

fun findContainingPairs(pairslist: List<Pair<Pair<Int,Int>, Pair<Int,Int>>>): List<Pair<Pair<Int,Int>, Pair<Int,Int>>> {
    return pairslist.filter { pair ->
        (pair.first.first <= pair.second.first && pair.first.second >= pair.second.second) ||
        (pair.second.first <= pair.first.first && pair.second.second >= pair.first.second)
    }
}

private fun readInput(fileName: String): List<Pair<Pair<Int,Int>, Pair<Int,Int>>> {
    var listOfPairs = mutableListOf<Pair<Pair<Int,Int>, Pair<Int,Int>>>()

    File(fileName).forEachLine {
        val elfPairs = it.split(",")
        val elf1 = elfPairs[0].split("-").map{it.toInt()}
        val elf2 = elfPairs[1].split("-").map{it.toInt()}

        listOfPairs.add(Pair(Pair(elf1[0], elf1[1]), Pair(elf2[0],elf2[1])))
    }

    return listOfPairs
}