fun <T> tspReverseTree(graph: MutableGraph<T>): Pair<MutableList<T>, Int?> {
    // Create variables to keep track of the best path and it's weight
    var bestPath = mutableListOf<T>()
    var minWeight: Int? = null

    for (i in graph.indices) {
        val nextRemainingNodes = graph.nodes().toMutableList()
        val nextNode = nextRemainingNodes.removeAt(i)

        val (checkedPath, checkedWeight) = tspReverseTree(graph, nextNode, nextRemainingNodes)

        if (checkedWeight == null) continue

        // If a path exists, and either no path has been found or this path is shorter than the current shortest
        // path, set the shortest path to this one
        if (minWeight == null || minWeight > checkedWeight) {
            bestPath = checkedPath
            minWeight = checkedWeight
        }
    }

    return Pair(bestPath, minWeight)
}

/**
 * Find the
 */
fun <T> tspReverseTree(graph: MutableGraph<T>, previousNode: T, remainingNodes: List<T>): Pair<MutableList<T>, Int?> {
    // Create variables to keep track of the best path and it's weight
    var bestPath = mutableListOf<T>()
    var minWeight: Int? = null

    when (remainingNodes.size) {
        // If the remaining nodes have less than 2 elements, something is wrong
        0, 1 -> throw IllegalArgumentException("remainingNodes must have at least two elements")

        2 -> {
            val orderA = mutableListOf(previousNode, remainingNodes[0], remainingNodes[1])
            val orderB = mutableListOf(previousNode, remainingNodes[1], remainingNodes[0])

            val weightA = graph.pathWeight(orderA)
            val weightB = graph.pathWeight(orderB)

            return if (weightA == null && weightB == null) {
                Pair(mutableListOf(), null)
            } else if (weightB == null || (weightA != null && weightA > weightB)) {
                Pair(orderA, weightA)
            } else {
                Pair(orderB, weightB)
            }
        }

        // If there are three or more options
        else -> {
            for (i in remainingNodes.indices) {
                val nextRemainingNodes = remainingNodes.toMutableList()
                val nextNode = nextRemainingNodes.removeAt(i)

                val prev2nextWeight = graph[previousNode, nextNode] ?: continue

                val (checkedPath, checkedWeight) = tspReverseTree(graph, nextNode, nextRemainingNodes)

                if (checkedWeight == null) continue

                checkedPath.addFirst(previousNode)
                val totalWeight = checkedWeight + prev2nextWeight

                // If a path exists, and either no path has been found or this path is shorter than the current shortest
                // path, set the shortest path to this one
                if (minWeight == null || minWeight > totalWeight) {
                    bestPath = checkedPath
                    minWeight = totalWeight
                }
            }
        }
    }

    return Pair(bestPath, minWeight)
}