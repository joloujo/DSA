import org.jetbrains.letsPlot.Stat
import kotlin.time.DurationUnit

fun main() {
    // Set the sizes of the graphs to test and the number of cycles to test for in each size
    val sizes = (4..6).toList()
    val cycles = 10

    // Set up lists to keep track of the total time taken, weight proportion with the true shortest path, and number of
    // times a path wasn't found
    val sumTimes = List(4) { MutableList(sizes.size) { 0.0 } }
    val weightProportion = List(4) { List(sizes.size) { mutableListOf<Double>() } }
    val nullCount = List(4) { MutableList(sizes.size) { 0 } }

    // For each size to test
    for (i in sizes.indices) {
        // Test for the number of cycles
        for (j in 1..cycles) {
            // Set up a random graph of the right size
            val graph = MutableGraph<Int>()
            graph.addAll(1..sizes[i])
            graph.randomize(nullProbability = 0.2, minWeight = 10, maxWeight = 100)

            // Run and time the TSP algorithms
            val bruteForce = kotlin.time.measureTimedValue { tspBruteForce(graph) }
            val reverseTree = kotlin.time.measureTimedValue { tspReverseTree(graph) }
            val nearestNeighbor = kotlin.time.measureTimedValue { tspNearestNeighbor(graph) }
            val twoOpt = kotlin.time.measureTimedValue { tsp2Opt(graph) }

            // Add the times to the right cell
            sumTimes[0][i] += bruteForce.duration.toDouble(DurationUnit.SECONDS)
            sumTimes[1][i] += reverseTree.duration.toDouble(DurationUnit.SECONDS)
            sumTimes[2][i] += nearestNeighbor.duration.toDouble(DurationUnit.SECONDS)
            sumTimes[3][i] += twoOpt.duration.toDouble(DurationUnit.SECONDS)

            // If there is a path
            if (bruteForce.value.second != null) {
                // save the length of the actual shortest path and add the right weight proportion to
                val actualShortest = bruteForce.value.second!!.toDouble()
                weightProportion[0][i].add(1.0)

                // If each algorithm found a path, add the proportional weight to the right cell, otherwise, add a null
                if (reverseTree.value.second != null) {
                    weightProportion[1][i].add(reverseTree.value.second!!.toDouble() / actualShortest)
                } else {
                    nullCount[1][i] += 1
                }
                if (nearestNeighbor.value.second != null) {
                    weightProportion[2][i].add(nearestNeighbor.value.second!!.toDouble() / actualShortest)
                } else {
                    nullCount[2][i] += 1
                }
                if (twoOpt.value.second != null) {
                    weightProportion[3][i].add(twoOpt.value.second!!.toDouble() / actualShortest)
                } else {
                    nullCount[3][i] += 1
                }
            } else {
                // If there was no path, add a null to all of them
                nullCount[0][i] += 1
                nullCount[1][i] += 1
                nullCount[2][i] += 1
                nullCount[3][i] += 1
            }
        }
    }

    // Compute the averages from the sums and collections
    val averageTimes = sumTimes.map { row -> row.map { it/cycles } }
    val averageWeightProportion = weightProportion.map { row -> row.map { it.average() } }

    // Create data labels for each algorithm
    val labels = listOf("Brute Force", "Reverse Tree", "Nearest Neighbor", "2Opt")

    // Print the average time for each algorithm and for each size
    println("Average Times")
    for (i in 0..3) {
        println("  ${labels[i]}")
        for (j in sizes.indices) {
            val timeString = "%.5f".format(averageTimes[i][j])
            println("    Size ${sizes[j]}: $timeString seconds")
        }
    }

    // Print the average weight proportion for each algorithm and for each size
    println("Average Weight Proportion")
    for (i in 0..3) {
        println("  ${labels[i]}")
        for (j in sizes.indices) {
            val weightString = "%.5f".format(averageWeightProportion[i][j])
            println("    Size ${sizes[j]}: $weightString")
        }
    }

    // Print the null count for each algorithm and for each size
    println("Null count")
    for (i in 0..3) {
        println("  ${labels[i]}")
        for (j in sizes.indices) {
            println("    Size ${sizes[j]}: ${nullCount[i][j]}")
        }
    }
}
