import java.util.Collections.max

/**
 * Find the best sequence alignment for two lists using the Smith–Waterman algorithm
 * @param listA the first list
 * @param listB the second list
 * @param penalty the penalty gap function for a gap of a given length
 * @param similarity the similarity score for two given items
 * @return a [Pair] of two sequences, with nulls included where there are gaps, for the best sequence alignment
 */
fun <T> smithWaterman(listA: List<T>, listB: List<T>, penalty: (Int) -> Int, similarity: (T, T) -> Int): Pair<MutableList<T?>, MutableList<T?>> {
    // Set up matrices for scoring and traceback
    val scoreMatrix = IntMatrix(listA.size + 1, listB.size + 1)
    val traceback = Matrix<Pair<Int, Int>>(listA.size + 1, listB.size + 1)

    // Populate the score matrix
    for (i in 1..listA.size) {
        for (j in 1..listB.size) {
            // Calculate the values for the different options
            val matchScore = scoreMatrix[i-1, j-1] + similarity(listA[i-1], listB[j-1]) // diagonal (match)
            val gap1Score = max((1..i).map { k -> scoreMatrix[i-k, j] - penalty(k) }) // up (gap)
            val gap2Score = max((1..j).map { l -> scoreMatrix[i, j-l] - penalty(l) }) // left (gap)

            // Find the best option and determine the values for the score and traceback matrices
            val (value, path) = max(
                listOf(
                    matchScore, gap1Score, gap2Score, 0).zip(listOf(
                    Pair(i-1, j-1), Pair(i-1, j), Pair(i, j-1), null)),
                compareBy {
                    it.first
                }
            )

            // add those values to the score and traceback matrices
            scoreMatrix[i, j] = value
            traceback[i, j] = path
        }
    }

    // Find the starting location for traceback
    var loc = scoreMatrix.maxLoc()

    // Create lists to store the sequences
    val sequenceA = mutableListOf<T?>()
    val sequenceB = mutableListOf<T?>()

    // Traceback
    while (traceback[loc.first, loc.second] != null) {
        // Determine the next location
        val next = traceback[loc.first, loc.second]!!

        // Based on the direction, populate the sequences
        when (loc - next) {
            // Diagonal case
            Pair(1, 1) -> {
                // Match, add both letters
                sequenceA.add(listA[loc.first-1])
                sequenceB.add(listB[loc.second-1])
            }
            // Up case
            Pair(1, 0) -> {
                // Skip the letter in sequence B
                sequenceA.add(listA[loc.first-1])
                sequenceB.add(null)
            }
            // Left case
            Pair(0, 1) -> {
                // Skip the letter in sequence A
                sequenceA.add(null)
                sequenceB.add(listB[loc.second-1])
            }
        }

        // Go to the next location
        loc = next
    }

    // Reverse the sequences because traceback happens in reverse
    sequenceA.reverse()
    sequenceB.reverse()

    return Pair(sequenceA, sequenceB)
}

/**
 * Find the best sequence alignment for two strings using the Smith–Waterman algorithm
 * @param stringA the first string
 * @param stringB the second string
 * @param penalty the penalty gap function for a gap of a given length
 * @param similarity the similarity score for two given items
 * @return a [Pair] of two sequences, with nulls included where there are gaps, for the best sequence alignment
 */
fun smithWaterman(stringA: String, stringB: String, penalty: (Int) -> Int, similarity: (Char, Char) -> Int): Pair<MutableList<Char?>, MutableList<Char?>> {
    // Convert the strings to lists of characters
    val charsA = stringA.toList()
    val charsB = stringB.toList()

    // Find the best sequence alignment
    return smithWaterman(charsA, charsB, penalty, similarity)
}

/**
 * Elementwise subtraction for [Pairs][Pair] of integers
 * @param other the [Pair] to subtract
 */
operator fun Pair<Int, Int>.minus(other: Pair<Int, Int>): Pair<Int, Int> {
    return Pair(this.first - other.first, this.second - other.second)
}