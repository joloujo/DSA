import kotlin.math.max

/**
 * A class to represent a directed weighted graph.
 */
class MutableGraph<N> : MutableCollection<N> {
    // Create data structures to store nodes and edges
    private val nodes = LinkedHashSet<N>()
    private val edges = mutableListOf<MutableList<Int?>>()

    override val size: Int
        // The size is the number of nodes
        get() = nodes.size

    /**
     * Check if the sizes of the underlying data structures match. Used for testing.
     * @return true if all sizes match, false if any don't
     */
    fun correctSizes(): Boolean {
        // Get the size
        val s = nodes.size
        // Make sure the number of edges is the same as the number of nodes, in both dimensions
        return edges.size == s && (edges.map { it.size == s }).all { it }
    }

    override fun clear() {
        // Clear the nodes and edges
        nodes.clear()
        edges.clear()
    }

    override fun add(element: N): Boolean {
        // If the node already exists, don't add it
        if (nodes.contains(element)) return false

        // Otherwise, add the node
        nodes.add(element)

        // Add a disconnected edge from each existing node to this node
        edges.forEach { it.add(null) }

        // Add a disconnected edge from this node to each existing node
        edges.add(MutableList(size) { null })

        return true
    }

    override fun addAll(elements: Collection<N>): Boolean {
        // Add each element, and return true if any were successfully added
        return elements.map { add(it) }.any()
    }

    /**
     * Adds all the elements of the specified collection to this collection.
     * @return true if any of the specified elements was added to the collection, false if the collection was not
     * modified.
     */
    fun addAll(vararg elements: N): Boolean {
        return addAll(elements.toSet())
    }

    /**
     * Set the edge [weight] [from] one node [to] another.
     * @param from the node to set the edge from
     * @param to the node to set the edge to
     * @param weight the edge weight of the edge
     */
    operator fun set(from: N, to: N, weight: Int?) {
        // Get the from and to indexes, which are the row and column of the value to set in edges
        val fromIndex = nodes.indexOf(from)
        val toIndex = nodes.indexOf(to)

        // If either node isn't in the graph, throw a NoSuchElementException
        if (fromIndex == -1) throw NoSuchElementException("Can't set edge weight from $from, it is not in the graph")
        if (toIndex == -1) throw NoSuchElementException("Can't set edge weight to $to, it is not in the graph")

        // If the elements do exist, then set the weight
        edges[fromIndex][toIndex] = weight
    }

    /**
     * Get the edge weight [from] one node [to] another.
     * @param from the node to set the edge from
     * @param to the node to set the edge to
     * @return the edge weight of the edge
     */
    operator fun get(from: N, to: N): Int? {
        // Get the from and to indexes, which are the row and column of the value to get in edges
        val fromIndex = nodes.indexOf(from)
        val toIndex = nodes.indexOf(to)

        // If either node isn't in the graph, throw a NoSuchElementException
        if (fromIndex == -1) throw NoSuchElementException("Can't get edge weight from $from, it is not in the graph")
        if (toIndex == -1) throw NoSuchElementException("Can't get edge weight to $to, it is not in the graph")

        // If the elements do exist, return the weight
        return edges[fromIndex][toIndex]
    }

    override fun isEmpty(): Boolean {
        // The graph is empty if there are no nodes
        return nodes.isEmpty()
    }

    override fun iterator(): MutableIterator<N> {
        // To iterate over the graph, iterate over the nodes
        return nodes.iterator()
    }

    override fun retainAll(elements: Collection<N>): Boolean {
        // Find which nodes need to be removed
        val toRemove = nodes.filter { it !in elements }

        // Remove the nodes that need to be removed
        return removeAll(toRemove)
    }

    /**
     * Retains only the elements in this collection that are contained in the specified collection.
     * @return true if any element was removed from the collection, false if the collection was not modified.
     */
    fun retainAll(vararg elements: N): Boolean {
        return retainAll(elements.toSet())
    }

    override fun removeAll(elements: Collection<N>): Boolean {
        // Remove the nodes that need to be removed
        return elements.map { remove(it) }.any()
    }

    /**
     * Removes all of this collection's elements that are also contained in the specified collection.
     * @return true if any of the specified elements was removed from the collection, false if the collection was not
     * modified.
     */
    fun removeAll(vararg elements: N): Boolean {
        return removeAll(elements.toSet())
    }

    override fun remove(element: N): Boolean {
        // Get the index of the node to remove
        val elementIndex = nodes.indexOf(element)

        // If the element doesn't exist, return false
        if (elementIndex == -1) return false

        // Otherwise, remove the element
        nodes.remove(element)

        // Remove the row and column of the edges that the node corresponded to
        edges.removeAt(elementIndex)
        edges.forEach { it.removeAt(elementIndex) }

        return true
    }

    override fun containsAll(elements: Collection<N>): Boolean {
        // The graph contains the nodes if they are in nodes
        return nodes.containsAll(elements)
    }

    /**
     * Checks if all elements in the specified collection are contained in this collection.
     */
    fun containsAll(vararg elements: N): Boolean {
        return containsAll(elements.toSet())
    }

    override fun contains(element: N): Boolean {
        // The graph contains the node if it is in nodes
        return nodes.contains(element)
    }

    override fun toString(): String {
        // Get the node strings. Upper left tile should be blank
        val labels = nodes.map { it.toString() }
        labels.addFirst("")

        // Create a variable to keep track of the longest tile
        var longest = 0

        // Create a 2D mutable list to store the tiles as they are created
        val tiles = MutableList(size+1) { MutableList(size+1) { "" } }

        // For each tile
        for (y in 0..size) {
            for (x in 0..size) {
                // The first row and column are labels, otherwise the tiles are from the matrix
                val tile: String = when {
                    y == 0 -> labels[x]
                    x == 0 -> labels[y]
                    else -> edges[y-1][x-1].toString()
                }

                // Set the tile to the string
                tiles[y][x] = tile

                // Keep track of the longest tile
                longest = max(tile.length, longest)
            }
        }

        // For each row of tiles
        return (tiles.map {row ->
            // Make each tile an equal length
            (row.map {tile ->
                "%${longest}s".format(tile)

            // And join them together with a space in between each one
            }).joinToString(separator = " ")

        // Then join each line together
        }).joinToString(separator = "\n")
    }

    /**
     * Get the weight of the given path
     * @param path the list of nodes to traverse
     * @return the sum of the weights of each pair of nodes in the path, or null if the path is disconnected
     */
    fun pathWeight(path: List<N>): Int? {
        // Create a variable to keep track of the total weight
        var weight = 0

        // For each pair of nodes, add their weight to the path weight. If the nodes are disconnected, return null
        for (i in 0..<path.size-1) {
            weight += get(path[i], path[i+1]) ?: return null
        }

        return weight
    }
}