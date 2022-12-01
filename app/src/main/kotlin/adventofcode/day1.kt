package adventofcode

import java.io.File

fun mainDay1(currentPath: String) {
    val filePath = "app/src/main/resources"
    val fileName = "input1.txt"

    val calories = readInput("${currentPath}/${filePath}/${fileName}")
    val totalCalories = getTotalCaloriesPerElf(calories)

    val maxCalories = totalCalories.max()
    println("Max Calories: ${maxCalories}")

    totalCalories.sortDescending()
    val topThreeMaxCaloriesSum = totalCalories.take(3).sum()
    println("Top 3 Calories Total: ${topThreeMaxCaloriesSum}")
}

private fun getTotalCaloriesPerElf(calories: List<String>): IntArray {
    val totalCalories = mutableListOf<Int>()

    var elfTotalCalories = 0
    calories.forEach {
        if(it == "") {
            totalCalories.add(elfTotalCalories)
            elfTotalCalories = 0
        } else {
            elfTotalCalories += it.toInt()
        }
    }

    return totalCalories.toIntArray()
}

private fun readInput(fileName: String): List<String> {
    val calories = mutableListOf<String>()
    File(fileName).forEachLine {
        calories.add(it)
    }
    return calories
}