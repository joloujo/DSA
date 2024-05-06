/**
 * Find an approximate shortest Hamiltonian path using the greedy Nearest Neighbor method.
 * @param graph the graph to find the path in
 * @return a pair of the shortest path that visits each node and it's path weight
 */
fun <T> tspNearestNeighbor(graph: MutableGraph<T>): Pair<MutableList<T>, Int?> {
    // Create variables to keep track of the best path and it's weight
    var bestPath = mutableListOf<T>()
    var minWeight: Int? = null

    // Try starting at every node in the graph
    for (i in graph.indices) {
        // Any node except the starting node can still be visited
        val remainingNodes = graph.nodes().toMutableList()
        val node = remainingNodes.removeAt(i)

        // Create variables to store the path, its weight, and if it's possible
        val path: MutableList<T> = mutableListOf(node)
        var weight: Int = 0
        var possible = true

        // Repeat until there are no more nodes to visit
        while (remainingNodes.isNotEmpty()) {
            // Create variables to keep track of the best node to visit next
            var minStep: Int? = null
            var nextIndex: Int? = null

            // Check each next node and pick the best one
            for (j in remainingNodes.indices) {
                val step = graph[path.last(), remainingNodes[j]]

                if (step != null && (minStep == null || minStep > step)) {
                    minStep = step
                    nextIndex = j
                }
            }

            // If it's impossible, break
            if (nextIndex == null) {
                possible = false
                break
            }

            // Step to the next nodes
            path.add(remainingNodes.removeAt(nextIndex))
            weight += minStep!!
        }

        // If a path exists, and either no path has been found or this path is shorter than the current shortest
        // path, set the shortest path to this one
        if (possible && (minWeight == null || minWeight > weight)) {
            bestPath = path
            minWeight = weight
        }
    }

    return Pair(bestPath, minWeight)
}