import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*

class tspTest {
    private val graph = MutableGraph<Char>()

    @BeforeEach
    fun beforeEach() {
        graph.clear()

        // Make this matrix
        //        A    B    C    D    E    F
        //   A    1    7    4    5 null    9
        //   B    5 null    7    9    3    5
        //   C    7    5    2    8 null null
        //   D null    8    1    6 null null
        //   E    1    7    4    2    5    9
        //   F    3    7 null    6    8    8

        val nodes = ('A'..'F').toList()
        val edges = listOf(
            listOf(   1,    7,    4,    5, null,    9,),
            listOf(   5, null,    7,    9,    3,    5,),
            listOf(   7,    5,    2,    8, null, null,),
            listOf(null,    8,    1,    6, null, null,),
            listOf(   1,    7,    4,    2,    5,    9,),
            listOf(   3,    7, null,    6,    8,    8,),
        )

        graph.addAll(nodes)

        for (y in edges.indices) {
            for (x in edges.indices) {
                graph[nodes[y], nodes[x]] = edges[y][x]
            }
        }
    }

    @Test
    fun tspBruteForceCorrectness() {
        // Test for completeness
        completeness(::tspBruteForce)

        // Actual shortest path is
        val path = listOf('E', 'D', 'C', 'B', 'F', 'A')
        val weight = 16;

        // Make sure this returns the shortest path
        assertEquals(Pair(path, weight), tspBruteForce(graph))
    }

    @Test
    fun tspReverseTreeCorrectness() {
        // Test for completeness
        completeness(::tspReverseTree)

        // Output should be
        val path = listOf('F', 'D', 'C', 'B', 'E', 'A')
        val weight = 16;

        // Make sure this returns what the output should be
        assertEquals(Pair(path, weight), tspReverseTree(graph))
    }

    @Test
    fun tspNearestNeighbor() {
        // Test for completeness
        completeness(::tspNearestNeighbor)

        // Output should be
        val path = listOf('F', 'A', 'C', 'B', 'E', 'D')
        val weight = 17;

        // Make sure this returns what the output should be
        assertEquals(Pair(path, weight), tspNearestNeighbor(graph))
    }

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

            val (path, weight) = tsp(randomGraph)

            if (weight == null) continue

            assertEquals(nodes.toSet(), path.toSet())
        }
    }
}