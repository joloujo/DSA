import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*

class tspTest {
    // Create a shared graph
    private val graph = MutableGraph<Char>()

    /**
     * Reset the graph before each test
     */
    @BeforeEach
    fun beforeEach() {
        graph.clear()

        // Make this matrix, which has known outcomes
        //        A    B    C    D    E    F    G    H    I
        //   A   54   84   80 null null null   22   76   20
        //   B null null   43   96   27   55   28   87   29
        //   C   40   47   54   89 null   19   43   68   13
        //   D   67   27   42 null   91   94   72 null   85
        //   E   36   38   93   39   34   51   53 null   35
        //   F   20   97   16   80   45   52   21 null   89
        //   G   62   37   65 null   64   39   81   73   74
        //   H   20   94 null   98   14   24   57   21   25
        //   I   39   26   58   84   90   89   25 null   27

        // Set up the nodes and edges
        val nodes = ('A'..'I').toList()
        val edges = listOf(
            listOf(  54,   84,   80, null, null, null,   22,   76,   20,),
            listOf(null, null,   43,   96,   27,   55,   28,   87,   29,),
            listOf(  40,   47,   54,   89, null,   19,   43,   68,   13,),
            listOf(  67,   27,   42, null,   91,   94,   72, null,   85,),
            listOf(  36,   38,   93,   39,   34,   51,   53, null,   35,),
            listOf(  20,   97,   16,   80,   45,   52,   21, null,   89,),
            listOf(  62,   37,   65, null,   64,   39,   81,   73,   74,),
            listOf(  20,   94, null,   98,   14,   24,   57,   21,   25,),
            listOf(  39,   26,   58,   84,   90,   89,   25, null,   27,),
        )

        // Add the nodes and edges
        graph.addAll(nodes)
        for (y in edges.indices) {
            for (x in edges.indices) {
                graph[nodes[y], nodes[x]] = edges[y][x]
            }
        }
    }

    /**
     * Test the Brute Force algorithm for completeness and correctness
     */
    @Test
    fun tspBruteForce() {
        // Test for completeness
        completeness(::tspBruteForce)

        // Actual shortest path is
        val path = listOf('H', 'A', 'G', 'F', 'C', 'I', 'B', 'E', 'D')
        val weight = 202;

        // Make sure this returns the shortest path
        assertEquals(Pair(path, weight), tspBruteForce(graph))
    }

    /**
     * Test the Reverse Tree algorithm for completeness and correctness
     */
    @Test
    fun tspReverseTree() {
        // Test for completeness
        completeness(::tspReverseTree)

        // Output should be
        val path = listOf('H', 'E', 'D', 'C', 'F', 'A', 'I', 'G', 'B')
        val weight = 216;

        // Make sure this returns what the output should be
        assertEquals(Pair(path, weight), tspReverseTree(graph))
    }

    /**
     * Test the Nearest Neighbor algorithm for completeness and correctness
     */
    @Test
    fun tspNearestNeighbor() {
        // Test for completeness
        completeness(::tspNearestNeighbor)

        // Output should be
        val path = listOf('D', 'B', 'E', 'I', 'G', 'F', 'C', 'A', 'H')
        val weight = 285;

        // Make sure this returns what the output should be
        assertEquals(Pair(path, weight), tspNearestNeighbor(graph))
    }

    /**
     * Test the 2-Opt algorithm for completeness and correctness
     */
    @Test
    fun tsp2Opt() {
        //Test for completeness
        completeness(::tsp2Opt)

        // Output should be
        val path = listOf('H', 'E', 'D', 'C', 'F', 'A', 'I', 'B', 'G')
        val weight = 208;

        // Make sure this returns what the output should be
        assertEquals(Pair(path, weight), tsp2Opt(graph))
    }

    /**
     * Test a TSP algorithm for completeness, meaning the output is a valid hamiltonian path
     */
    private fun completeness(tsp: (graph: MutableGraph<Char>) -> Pair<List<Char>, Int?>) {
        // Create an 8x8 graph with no edges
        val emptyGraph = MutableGraph<Char>()
        emptyGraph.addAll('A'..'H')

        // Make sure that no path is returned and that the weight is null
        assertEquals(Pair(listOf<Char>(), null), tsp(emptyGraph))

        // Test to make sure that the paths contain all nodes a few times
        for (i in 1..20) {
            // Create a random 8 node graph
            val randomGraph = MutableGraph<Char>()
            val nodes = ('A'..'H').toList()
            randomGraph.addAll(nodes)
            randomGraph.randomize()

            // Calculate the shortest path
            val (path, weight) = tsp(randomGraph)

            // If it's impossible, then continue
            if (weight == null) continue

            // If it is possible, make sure the path contains every node
            assertEquals(nodes.toSet(), path.toSet())
        }
    }
}