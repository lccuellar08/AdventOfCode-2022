package adventofcode

import java.io.File

fun mainDay3(currentPath: String) {
    val filePath = "app/src/main/resources"
    val fileName = "input3.txt"

    val itemsList = readInput("${currentPath}/${filePath}/${fileName}")
    val commonItems = itemsList.map { findCommonItem(it) }

    val priorities = commonItems.map { getPriority(it) }
    val totalPriorities = priorities.sum()
    println("Total priorities; $totalPriorities")

    val badges = itemsList
        .also { require(it.size % 3 == 0) }
        .chunked(3)
        .map {(items1, items2, items3) -> findBadge(items1, items2, items3)}

    val badgesPriority = badges.map{ getPriority(it)}
    val totalBadgesPriority = badgesPriority.sum()

    println("Total badges priorities; $totalBadgesPriority")

}

private fun findBadge(items1: String, items2: String, items3: String): Char {
    val charMap = mutableMapOf<Char, Int>()
    items1.toSet().forEach {
        charMap[it] = 1
    }
    items2.toSet().forEach {
        if(charMap.containsKey(it)) {
            charMap[it] = charMap[it]?.plus(1)!!
        } else {
            charMap[it] = 1
        }
    }
    items3.toSet().forEach {
        if(charMap.containsKey(it)) {
            charMap[it] = charMap[it]?.plus(1)!!
        } else {
            charMap[it] = 1
        }
    }

    return charMap.filterValues { it == 3 }.keys.first()
}

private fun findCommonItem(items: String): Char {
    val midPoint = items.length/2
    val firstCompartment = items.substring(0, midPoint)
    val secondCompartment = items.substring(midPoint, items.length)

    val charMap = mutableMapOf<Char, Int>()
    firstCompartment.forEach {
        charMap[it] = 1
    }

    secondCompartment.forEach {
        if(charMap.containsKey(it)) {
            return it
        }
    }

    return charMap.filterValues { it == 2 }.keys.first()
}

private fun getPriority(item: Char): Int {
    if(item.isLowerCase()) {
        return item.code - 96
    } else {
        return item.code - 64 + 26
    }
}

private fun readInput(fileName: String): List<String> {
    val itemsList = mutableListOf<String>()

    File(fileName).forEachLine {
        itemsList.add(it)
    }

    return itemsList
}