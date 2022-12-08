package adventofcode

import java.io.File

fun mainDay8(currentPath: String) {
    val filePath = "app/src/main/resources"
    val fileName = "input8.txt"

    val listOfTrees = readInput("${currentPath}/${filePath}/${fileName}")

//    listOfTrees.forEach {
//        it.forEach {
//            print(it)
//        }
//        println()
//    }

    val treeGrid = createGrid(listOfTrees)
    printGrid(treeGrid)

    val visibleTrees = findVisibleTrees(treeGrid)
    println("Total visible trees: ${visibleTrees.size}")

    val scenicScores = getScenicScores(treeGrid)
//    println(scenicScores)
    println("Max scenic score: ${scenicScores.max()}")
}

private fun getScenicScores(grid: Array<IntArray>): List<Int> {
    val scenicScores = mutableListOf<Int>()

    for(r in 1 until grid[0].size - 1) {
        for(c in 1 until grid[1].size - 1) {
            val scenicScore = getScenicScore(grid, c, r)
            scenicScores.add(scenicScore)
        }
    }

    return scenicScores
}

private fun findVisibleTrees(grid: Array<IntArray>): Set<Pair<Int,Int>> {
    val visibleTreesSet = mutableSetOf<Pair<Int,Int>>()

    for(r in 1 until grid[0].size - 1) {
        for(c in 1 until grid[1].size - 1) {
            if(checkVisibility(grid, c, r))
                visibleTreesSet.add(Pair(r,c))
        }
    }

    return visibleTreesSet
}

private fun getScenicScore(grid: Array<IntArray>, x: Int, y: Int): Int {
    if(x == 1 || x == grid[y].size - 2 || y == 1 || y == grid.size - 2) {
        return 0
    }

    val thisTree = grid[y][x]
    var scenicValue = 1

    // Check left
    var counter = 0
    for(c in x-1 downTo 1) {
        counter += 1
        if(grid[y][c] >= thisTree) {
            break
        }
    }
    scenicValue *= counter
//    println("Left: $counter")

    // Check right
    counter = 0
    for(c in x+1 until grid[y].size) {
        counter += 1
        if(grid[y][c] >= thisTree) {
            break
        }
    }
    scenicValue *= counter
//    println("Right: $counter")

    // Check up
    counter = 0
    for(r in y-1 downTo 1) {
        counter += 1
        if(grid[r][x] >= thisTree) {
            break
        }
    }
    scenicValue *= counter
//    println("Up: $counter")

    // Check down
    counter = 0
    for(r in y+1 until grid.size - 1) {
        counter += 1
        if(grid[r][x] >= thisTree) {
            break
        }
    }
    scenicValue *= counter
//    println("Down: $counter")

    return scenicValue
}

private fun checkVisibility(grid: Array<IntArray>, x: Int, y: Int): Boolean {
    val thisTree = grid[y][x]

    // Check left
    var visible = true
    for(c in x-1 downTo 0) {
        if(grid[y][c] >= thisTree) {
            visible = false
            break
        }
    }
    if(visible) {
        return true
    }

    // Check right
    visible = true
    for(c in x+1 until grid[y].size) {
        if(grid[y][c] >= thisTree) {
            visible = false
            break
        }
    }

    if(visible) {
        return true
    }

    // Check up
    visible = true
    for(r in y-1 downTo 0) {
        if(grid[r][x] >= thisTree) {
            visible =  false
            break
        }
    }
    if(visible) {
        return true
    }

    // Check down
    visible = true
    for(r in y+1 until grid.size) {
        if(grid[r][x] >= thisTree) {
            visible = false
            break
        }
    }

    return visible
}

private fun printGrid(grid: Array<IntArray>) {
    grid.forEach {row ->
        row.forEach {
            if(it > -1)
                print(it)
        }
        println()
    }
}

private fun createGrid(listOfTrees: List<List<Int>>): Array<IntArray> {

    val rows = listOfTrees.size + 2
    val cols = listOfTrees[0].size + 2

    val arrayOfTrees = Array(rows) { IntArray(cols) { -1 } }

    for(r in 0 until listOfTrees.size) {
        for(c in 0 until listOfTrees[0].size) {
            arrayOfTrees[r+1][c+1] = listOfTrees[r][c]
        }
    }

    return arrayOfTrees
}

private fun readInput(fileName: String): List<List<Int>> {
    val listOfTrees = mutableListOf<List<Int>>()

    File(fileName).forEachLine {
        val tokens = it.map{s -> s.digitToInt()}
        listOfTrees.add(tokens)
    }

    return listOfTrees
}