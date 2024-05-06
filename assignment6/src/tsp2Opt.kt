/**
 * Find an approximate shortest Hamiltonian path using the 2-opt method. This algorithm optimizes an already known
 * solution, which is found with the Reverse Tree algorithm.
 * @param graph the graph to find the path in
 * @return a pair of the shortest path that visits each node and it's path weight
 */
fun <T> tsp2Opt(graph: MutableGraph<T>): Pair<MutableList<T>, Int?> {
    // Find a solution
    val (path, weight) = tspReverseTree(graph)

    // If there is no solution, then return such
    if (weight == null) return Pair(mutableListOf(), null)

    // Otherwise optimize the solution
    return tsp2Opt(graph, path)
}

/**
 * Find an approximate shortest Hamiltonian path using the 2-opt method. This algorithm optimizes an already known,
 * given solution.
 * @param graph the graph to find the path in
 * @param start the path to optimize
 * @return a pair of the shortest path that visits each node and it's path weight
 */
fun <T> tsp2Opt(graph: MutableGraph<T>, start: List<T>): Pair<MutableList<T>, Int?> {
    // Create variables to store the best path and it's weight
    var path = start
    var minWeight = graph.pathWeight(start)!!

    // Create a variable to track if improvements are being made
    var improved = true

    // Continue until there is no optimization left to be done
    while (improved) {

        // reset improved
        improved = false

        // For each pair of nodes
        for (i in graph.indices) {
            for (j in graph.indices) {

                // If the nodes are the same, don't try to swap them
                if (i == j) continue

                // Try to swap them
                val newPath = path.swap(i, j)
                val newWeight = graph.pathWeight(newPath)

                // If there is an improvements, update minWeight, path, and improved
                if (newWeight != null && newWeight < minWeight) {
                    minWeight = newWeight
                    path = newPath
                    improved = true
                }
            }
        }
    }

    // Return the optimized path
    return Pair(path.toMutableList(), minWeight)
}

/**
 * Swap two elements in a list
 * @param a the index of the first element to swap
 * @param b the index of the second element to swap
 * @return a copy of the list with elements a and b swapped
 */
fun <T> List<T>.swap(a: Int, b: Int): List<T> {
    // Create a copy of the list
    val list = this.toMutableList()

    // Save the value of a
    val aValue = list[a]

    // b -> a
    list[a] = list[b]
    // a -> b
    list[b] = aValue

    return list
}