fun <T> tspNearestNeighbor(graph: MutableGraph<T>): Pair<MutableList<T>, Int?> {
    // Create variables to keep track of the best path and it's weight
    var bestPath = mutableListOf<T>()
    var minWeight: Int? = null

    for (i in graph.indices) {
        val remainingNodes = graph.nodes().toMutableList()
        val node = remainingNodes.removeAt(i)

        val path: MutableList<T> = mutableListOf(node)
        var weight: Int = 0
        var possible = true

        while (remainingNodes.isNotEmpty()) {
            var minStep: Int? = null
            var nextIndex: Int? = null

            for (j in remainingNodes.indices) {
                val step = graph[path.last(), remainingNodes[j]]

                if (step != null && (minStep == null || minStep > step)) {
                    minStep = step
                    nextIndex = j
                }
            }

            if (nextIndex == null) {
                possible = false
                break
            }


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