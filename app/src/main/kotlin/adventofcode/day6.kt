package adventofcode

import java.io.File

fun mainDay6(currentPath: String) {
    val filePath = "app/src/main/resources"
    val fileName = "input6.txt"

    val stringBuffer = readInput("${currentPath}/${filePath}/${fileName}")
    val startOfPacket = findStartOfPacket(stringBuffer, 4)

    println("Start of packet: $startOfPacket")

    val startOfMessage = findStartOfPacket(stringBuffer, 14)
    println("Start of packet: $startOfMessage")
}

private fun findStartOfPacket(stringBuffer: String, windowSize: Int): Int {
    for(i in 0..stringBuffer.length) {
        val subBuffer = stringBuffer.substring(i, i + windowSize)
        if(subBuffer.toSet().size == windowSize)
            return (i + windowSize)
    }
    return 0
}

private fun readInput(fileName: String): String {
    return File(fileName).readText()
}