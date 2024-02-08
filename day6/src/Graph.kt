import java.util.ArrayDeque

class Graph<VertexType> {
    private var vertices: MutableSet<VertexType> = mutableSetOf()
    private var edges: MutableMap<VertexType, MutableSet<VertexType>> = mutableMapOf()

    /**
     * Add the vertex [v] to the graph
     * @param v the vertex to add
     * @return true if the vertex is successfully added, false if the vertex
     *   was already in the graph
     */
    fun addVertex(v: VertexType): Boolean {
        if (vertices.contains(v)) {
            return false
        }
        vertices.add(v)
        return true
    }

    /**
     * Add an edge between vertex [from] connecting to vertex [to]
     * @param from the vertex for the edge to originate from
     * @param to the vertex to connect the edge to
     * @return true if the edge is successfully added and false if the edge
     *     can't be added or already exists
     */
    fun addEdge(from: VertexType, to: VertexType): Boolean {
        if (!vertices.contains(from) || !vertices.contains(to)) {
            return false
        }
        edges[from]?.also { currentAdjacent ->
            if (currentAdjacent.contains(to)) {
                return false
            }
            currentAdjacent.add(to)
        } ?: run {
            edges[from] = mutableSetOf(to)
        }
        return true
    }

    /**
     * Clear all vertices and edges
     */
    fun clear() {
        vertices = mutableSetOf()
        edges = mutableMapOf()
    }

    /**
     * Determines whether it is possible to reach the [target] value from the [root] value using breadth first search
     *
     * *Disclaimer: I didn't actually test if this does BFS properly, although it should conceptually. I hope to come
     *  back to this and test it at some point.*
     *
     * @return true if the [target] is reachable, false if the [target] is unreachable
     */
    fun reachableBFS(root: VertexType, target: VertexType): Boolean {
        // Uses the structure defined in the day 6 assignment

        val toVisit = mutableSetOf(root)
        val priorityList = ArrayDeque(listOf(root))

        while (priorityList.isNotEmpty()) {
            val n = priorityList.removeFirst()
            if (n == target) {
                return true
            }
            edges[n]?.forEach {
                if (it !in toVisit) {
                    priorityList.addLast(it) // treat the deque as a queue
                    toVisit.add(it)
                }
            }
        }

        return false
    }

    /**
     * Determines whether it is possible to reach the [target] value from the [root] value using depth first search
     *
     * *Disclaimer: I didn't actually test if this does DFS properly, although it should conceptually. I hope to come
     *  back to this and test it at some point.*
     *
     * @return true if the [target] is reachable, false if the [target] is unreachable
     */
    fun reachableDFS(root: VertexType, target: VertexType): Boolean {
        // Uses the structure defined in the day 6 assignment

        val toVisit = mutableSetOf(root)
        val priorityList = ArrayDeque(listOf(root))

        while (priorityList.isNotEmpty()) {
            val n = priorityList.removeFirst()
            if (n == target) {
                return true
            }
            edges[n]?.forEach {
                if (it !in toVisit) {
                    priorityList.addFirst(it) // treat the deque as a stack
                    toVisit.add(it)
                }
            }
        }

        return false
    }
}