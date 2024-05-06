/**
 * Find the exact shortest Hamiltonian path using brute force. Recurses through every permutation of nodes and checks
 * the path weight of that permutation.
 * @param graph the graph to find the path in
 * @return a pair of the shortest path that visits each node and it's path weight
 */
fun <T> tspBruteForce(graph: MutableGraph<T>): Pair<MutableList<T>, Int?> {
    return tspBruteForce(graph, listOf(), graph.nodes().toList())
}

/**
 * Find the exact shortest Hamiltonian path using brute force. Recurses through every permutation of nodes and checks
 * the path weight of that permutation.
 * @param graph the graph to find the path in
 * @param currentPath the current path to add to. Used for recursion
 * @param remainingNodes the nodes to choose from to add to the current path. Cannot be empty. Used for recursion
 * @return a pair of the shortest path starting with [currentPath] and using all [remainingNodes], and it's path weight.
 * If no path exists that visits all nodes, returns an empty path list and a null path weight
 */
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
        }

        // If there are two or more options
        else -> {
            // Recurse through each permutation by creating a branch for each next node
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