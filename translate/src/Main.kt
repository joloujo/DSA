/** My code from Advent of Code 2023 day 9 part 1 translated from Python to Kotlin */

import java.io.File

/**
 * Returns the difference between each adjacent element in the [input] list
 */
fun diff(input: List<Int>): List<Int> {
    // Create a list to store the differences
    val result: List<Int> = mutableListOf()

    // add the difference between each element to the result
    for (i in 0..<input.count() - 1) {
        val difference = input[i + 1] - input[i]
        result.addLast(difference)
    }

    return result
}

/**
 * Returns the next number in the [input] series
 */
fun predict(input: List<Int>): Int {
    // Find the difference of the input
    val difference = diff(input)

    // if all the input numbers are the same, the rate of change is 0
    if (difference.all {it == 0}) {
        return input.last()
    }
    // otherwise, find the next number in the difference series, then return its rate of change plus the last number
    else {
        val change = predict(difference)
        return input.last() + change
    }
}

/**
 * Returns the sum of the predicted numbers from a file with name [pathname]
 */
fun total(pathname: String): Int {
    // Load the data
    val text = File(pathname).readLines()

    // Create a variable to store the answer
    var answer = 0

    // Find the sum of all the predicted numbers
    for (line in text) {
        val numbers = line.split(" ").map {it.toInt()}
        answer += predict(numbers)
    }

    return answer
}

fun main() {
    println(total("src/day9.txt"))
}