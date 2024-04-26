fun main() {
    // Print sequence alignment from Wikipedia (https://en.wikipedia.org/wiki/Smith%E2%80%93Waterman_algorithm)
    println("GGTTGACTA vs TGTTACGG:")
    printSW(smithWaterman("GGTTGACTA", "TGTTACGG", {a -> 2 * a}, {a, b -> if (a == b) 3 else -3}))

    println("")

    // Print sequence alignment from ResearchGate by Dzmitry Razmyslovich
    // (https://www.researchgate.net/figure/An-example-of-an-alignment-by-Smith-Waterman-algorithm_fig2_278675646)
    println("AGCACACA vs ACACACTA:")
    printSW(smithWaterman("AGCACACA", "ACACACTA", {a ->  a}, {a, b -> if (a == b) 2 else -1}))
}

/**
 * Prints the result of a Smith-Waterman sequence alignment with string instead of lists. Also has a match string to
 * illustrate where values were skipped
 * @param result the pair of sequences output by the [smithWaterman] function
 */
fun printSW(result: Pair<List<Char?>, List<Char?>>) {
    // Set up strings to add to
    var stringA = ""
    var stringB = ""
    var matches = ""

    // For each pair of characters
    for (pair in result.first.zip(result.second)) {
        // If the characters are the same, there's a match
        if (pair.first == pair.second) {
            stringA += pair.first
            stringB += pair.second
            matches += "|"
            continue
        }

        // Otherwise, there isn't a match
        matches += " "

        // If either character is null, then that one was skipped, add the other sequences letter
        if (pair.first == null) {
            stringA += '-'
            stringB += pair.second
        } else if (pair.second == null) {
            stringA += pair.first
            stringB += '-'
        }
    }

    // Print the strings
    println(stringA)
    println(matches)
    println(stringB)
}