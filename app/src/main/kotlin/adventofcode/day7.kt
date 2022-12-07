package adventofcode

import java.io.File

fun mainDay7(currentPath: String) {
    val filePath = "app/src/main/resources"
    val fileName = "input7.txt"

    val outputStrings = readInput("${currentPath}/${filePath}/${fileName}")
    val head = createFS(outputStrings)
    calculateSizes(head)
    printNode(head, "")

    val largeDirectoriesSum = sumOfLargeDirectories(head)
    println("Sum of large directories: ${largeDirectoriesSum}")

    val unusedSpace = 70000000 - head.size
//    val minNode = findSmallestDeletableDirectory(head, head, unusedSpace)

    val listOfDirs = getListOfDirectories(head, mutableListOf())
    println(unusedSpace)
    val minNode = findSmallestDeletableDirectory(listOfDirs, 30000000 - unusedSpace)
    println("Min node: ${minNode.name}: ${minNode.size}")
}

private fun findSmallestDeletableDirectory(listDirs: List<NodeFS>, minSize: Long): NodeFS {
    var newList = listDirs.filter { it.size > minSize }
    var currMin = newList.first()

    newList.forEach{
        if(it.size <= currMin.size)
            currMin = it
    }

    return currMin
}

private fun getListOfDirectories(head: NodeFS, listDirs: MutableList<NodeFS>): List<NodeFS> {
    head.directories.forEach {
        listDirs.addAll(getListOfDirectories(it, mutableListOf()))
    }

    listDirs.add(head)
    return listDirs
}

private fun sumOfLargeDirectories(head: NodeFS): Long {
    val largeDirectories = head.directories.filter { it.size <= 100000}
    val largeDirectoriesSum = largeDirectories.sumOf { it.size }
    return largeDirectoriesSum + head.directories.map { sumOfLargeDirectories(it)}.sum()
}

private fun calculateSizes(head: NodeFS): Long {
    val childrenDirectorySize = head.directories.map{ calculateSizes(it)}.sum()
    head.size = head.files.values.sum() + childrenDirectorySize
    return head.size
}

private fun printNode(head: NodeFS, tabs: String) {
    println("${tabs}- ${head.name} (dir, size=${head.size})")
    head.directories.forEach {
        printNode(it, "${tabs}\t")
    }
    head.files.forEach {(name,size) ->
        println("${tabs}\t- $name (file, size=$size)")
    }
}

private fun createFS(outputStrings: List<String>): NodeFS {
    val head = NodeFS(null, "/", 0, mutableListOf(), mutableMapOf())
    var current = head

    outputStrings.forEach {
        if(it.contains("$")) {
            val tokens = it.split(" ")
            when(tokens[1]) {
                "cd" -> {
                    if(tokens[2] == "..") {
                        if(current.prev != null)
                            current = current.prev!!
                    } else {
                        val newCurrent = current.directories.findLast { dir -> dir.name == tokens[2] }
                        current = newCurrent!!
                    }
                }
                "ls" -> {

                }
            }
        } else {
            val tokens = it.split(" ")
            if(tokens[0] == "dir") {
                if(current.directories.filter { dir -> dir.name == tokens[1] }.isEmpty()) {
                    val newDir = NodeFS(current, tokens[1], 0, mutableListOf(), mutableMapOf())
                    current.directories.add(newDir)
                }
            } else {
                if(current.files.filter { (name,_) -> name == tokens[1] }.isEmpty()) {
                    current.files[tokens[1]] = tokens[0].toLong()
                }
            }
        }
    }

    return head
}

private data class NodeFS (val prev: NodeFS?, val name: String, var size: Long, val directories: MutableList<NodeFS>, val files: MutableMap<String, Long>)

private fun readInput(fileName: String): List<String> {
    val outputList = mutableListOf<String>()

    File(fileName).forEachLine {
        outputList.add(it)
    }

    return outputList
}