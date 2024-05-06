/**
 * Find an approximate shortest Hamiltonian path using a tree search that avoids null paths.
 * @param graph the graph to find the path in
 * @return a pair of the shortest path that visits each node and it's path weight
 */
fun <T> tspReverseTree(graph: MutableGraph<T>): Pair<MutableList<T>, Int?> {
    // Create variables to keep track of the best path and it's weight
    var bestPath = mutableListOf<T>()
    var minWeight: Int? = null

    // Try starting at each node in the graph
    for (i in graph.indices) {
        // Any node except the starting node can still be visited
        val nextRemainingNodes = graph.nodes().toMutableList()
        val nextNode = nextRemainingNodes.removeAt(i)

        // Recurse
        val (checkedPath, checkedWeight) = tspReverseTree(graph, nextNode, nextRemainingNodes)

        // If there is no possible path, continue
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
 * Find an approximate shortest Hamiltonian path using a tree search that avoids null paths, starting at a given node.
 * @param graph the graph to find the path in
 * @param previousNode the last node visited
 * @param remainingNodes the nodes to choose from to add to the current path. Cannot be empty. Used for recursion
 * @return a pair of the shortest path that visits each node and it's path weight
 */
fun <T> tspReverseTree(graph: MutableGraph<T>, previousNode: T, remainingNodes: List<T>): Pair<MutableList<T>, Int?> {
    // Create variables to keep track of the best path and it's weight
    var bestPath = mutableListOf<T>()
    var minWeight: Int? = null

    when (remainingNodes.size) {
        // If the remaining nodes have less than 2 elements, something is wrong
        0, 1 -> throw IllegalArgumentException("remainingNodes must have at least two elements")

        // If there are only two remaining nodes, pick one of the two orders
        2 -> {
            // Create the paths from the previous node through the two remaining nodes
            val orderA = mutableListOf(previousNode, remainingNodes[0], remainingNodes[1])
            val orderB = mutableListOf(previousNode, remainingNodes[1], remainingNodes[0])

            // Calculate the path weight of the two paths
            val weightA = graph.pathWeight(orderA)
            val weightB = graph.pathWeight(orderB)

            return if (weightA == null && weightB == null) {
                // If there is no path, return null
                Pair(mutableListOf(), null)
            } else if (weightB == null || (weightA != null && weightA > weightB)) {
                // Otherwise if B is null or A is non-null and larger, return A
                Pair(orderA, weightA)
            } else {
                // Otherwise, A is null or B is larger, so return B
                Pair(orderB, weightB)
            }
        }

        // If there are three or more options
        else -> {
            // Check moving to each next node
            for (i in remainingNodes.indices) {
                // Take the next node out of the remaining nodes
                val nextRemainingNodes = remainingNodes.toMutableList()
                val nextNode = nextRemainingNodes.removeAt(i)

                // keep track of the weight from the previous node to the chosen node
                val prev2nextWeight = graph[previousNode, nextNode] ?: continue

                // Recurse, and check the possible paths
                val (checkedPath, checkedWeight) = tspReverseTree(graph, nextNode, nextRemainingNodes)

                // if it's impossible, continue
                if (checkedWeight == null) continue

                // Add the weight from the previous node to this node to the weight of the best next path
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