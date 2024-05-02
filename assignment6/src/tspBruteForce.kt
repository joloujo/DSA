fun <T> tspBruteForce(graph: MutableGraph<T>): Pair<MutableList<T>, Int?> {
    return tspBruteForce(graph, listOf(), graph.nodes().toList())
}

fun <T> tspBruteForce(graph: MutableGraph<T>, currentPath: List<T>, remainingNodes: List<T>): Pair<MutableList<T>, Int?> {
    // Create variables to keep track of the best path and it's weight
    var bestPath = mutableListOf<T>()
    var minWeight: Int? = null

    when (remainingNodes.size) {
        // If the remaining nodes are empty, something is wrong
        0 -> throw IllegalArgumentException("remainingNodes cannot be empty")

        // If there is only one option, try it
        1 -> {
            bestPath = currentPath.toMutableList()
            bestPath.add(remainingNodes[0])
            minWeight = graph.pathWeight(bestPath)

            println("$bestPath | $minWeight")
        }

        // If there are two or more options
        else -> {
            // Recurse through each permutation
            for (i in remainingNodes.indices) {
                // Add the next node to the current path
                val nextPath = currentPath.toMutableList()
                nextPath.add(remainingNodes[i])

                // Remove the next node from the remaining nodes
                val nextRemainingNodes = remainingNodes.toMutableList()
                nextRemainingNodes.removeAt(i)

                // Find the path weight
                val (checkedPath, checkedWeight) = tspBruteForce(graph, nextPath, nextRemainingNodes)

                // If a path exists, and either no path has been found or this path is shorter than the current shortest
                // path, set the shortest path to this one
                if (checkedWeight != null && (minWeight == null || minWeight > checkedWeight)) {
                    bestPath = checkedPath
                    minWeight = checkedWeight
                }
            }
        }
    }

    return Pair(bestPath, minWeight)
}