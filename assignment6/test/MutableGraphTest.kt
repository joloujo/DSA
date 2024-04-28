import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*

class MutableGraphTest {
    // Create a graph to test on
    private val g = MutableGraph<Char>()

    /**
     * Reset the graph before each test. It will start with nodes A, B, C, and D, and no edges.
     */
    @BeforeEach
    fun beforeEach() {
        // Clear the graph
        g.clear()

        // Repopulate the graph
        g.addAll('A', 'B', 'C', 'D')
    }

    /**
     * Test getting the size of the graph
     */
    @Test
    fun size() {
        // Make sure the size is 4
        assertEquals(4, g.size)

        // Make sure the sizes of the underlying data structures agree
        assertTrue(g.correctSizes())
    }

    /**
     * Test clearing the graph
     */
    @Test
    fun clear() {
        // Clear the graph
        g.clear()

        // Make sure the graph is empty
        assertEquals(0, g.size)

        // Make sure the sizes of the underlying data structures agree
        assertTrue(g.correctSizes())
    }

    /**
     * Test adding a node to the graph
     */
    @Test
    fun add() {
        // Add a node
        g.add('E')

        // Make sure the size goes up to 5
        assertEquals(5, g.size)

        // Make sure E is in the graph
        assertTrue(g.contains('E'))

        // Make sure the sizes of the underlying data structures agree
        assertTrue(g.correctSizes())
    }

    /**
     * Test adding multiple nodes to the graph
     */
    @Test
    fun addAll() {
        // Add two nodes to the graph
        g.addAll('E', 'F')

        // Make sure the size goes up to 6
        assertEquals(6, g.size)

        // Make sure E and F are in the graph
        assertTrue(g.containsAll('E', 'F'))

        // Make sure the sizes of the underlying data structures agree
        assertTrue(g.correctSizes())
    }

    /**
     * Test setting the edge weight between two nodes in the graph
     */
    @Test
    fun set() {
        // Make sure that the edge starts off empty
        assertEquals(null, g['A', 'B'])

        // Change the edge weight and make sure it changes
        g['A', 'B'] = 1
        assertEquals(1, g['A', 'B'])

        // Make sure that setting a non-existing element throws an exception
        assertThrows<NoSuchElementException> { g['A', 'E'] = 1 }

        // Make sure the sizes of the underlying data structures agree
        assertTrue(g.correctSizes())
    }

    /**
     * Test the [isEmpty()][MutableGraph.isEmpty] method of the graph
     */
    @Test
    fun isEmpty() {
        // Make sure the graph doesn't start empty
        assertFalse(g.isEmpty())

        // Clear the graph and make sure it ends up empty
        g.clear()
        assertTrue(g.isEmpty())

        // Make sure the sizes of the underlying data structures agree
        assertTrue(g.correctSizes())
    }

    /**
     * Test getting the iterator of the graph
     */
    @Test
    operator fun iterator() {
        // Create a list to iterate over that has what the nodes should be
        val testList = listOf('A', 'B', 'C', 'D')

        // Make sure that all the nodes are correct when iterated over
        for (pair in testList.zip(g)) {
            assertEquals(pair.first, pair.second)
        }
    }

    /**
     * Test retaining nodes in the graph
     */
    @Test
    fun retainAll() {
        // Get rid of nodes A and D
        g.retainAll('B', 'C')

        // Make sure the graph contains the right nodes
        assertFalse(g.contains('A'))
        assertTrue(g.containsAll('B', 'C'))
        assertFalse(g.contains('D'))

        // Make sure the size is correct
        assertEquals(2, g.size)

        // Make sure the sizes of the underlying data structures agree
        assertTrue(g.correctSizes())
    }

    /**
     * Test removing nodes in the graph
     */
    @Test
    fun removeAll() {
        // Get rid of nodes B and C
        g.removeAll('B', 'C')

        // Make sure the graph contains the right nodes
        assertTrue(g.containsAll('A', 'D'))
        assertFalse(g.contains('B'))
        assertFalse(g.contains('C'))

        // Make sure the size is correct
        assertEquals(2, g.size)

        // Make sure the sizes of the underlying data structures agree
        assertTrue(g.correctSizes())
    }

    /**
     * Test removing one node in the graph
     */
    @Test
    fun remove() {
        // Get rid of node B
        g.remove('B')

        // Make sure the graph contains the right nodes
        assertTrue(g.containsAll('A', 'C', 'D'))
        assertFalse(g.contains('B'))

        // Make sure the size is correct
        assertEquals(3, g.size)

        // Make sure the sizes of the underlying data structures agree
        assertTrue(g.correctSizes())
    }

    /**
     * Test the [containsAll()][MutableGraph.containsAll] method of the graph
     */
    @Test
    fun containsAll() {
        // Make sure the graph contains the right nodes
        assertTrue(g.containsAll('A', 'B', 'C', 'D'))
        assertFalse(g.containsAll('A', 'B', 'C', 'D', 'E'))
    }

    /**
     * Make sure the graph contains the correct nodes
     */
    @Test
    fun contains() {
        // Make sure the graph contains the right nodes
        assertTrue(g.contains('A'))
        assertTrue(g.contains('B'))
        assertTrue(g.contains('C'))
        assertTrue(g.contains('D'))
        assertFalse(g.contains('E'))
    }

    /**
     * Check the string representation of the graph
     */
    @Test
    fun graphToString() {
        // Create the string representation of the graph by hand and make sure it's the same
        assertEquals(
            "        A    B    C    D\n" +
                    "   A null null null null\n" +
                    "   B null null null null\n" +
                    "   C null null null null\n" +
                    "   D null null null null",
            g.toString()
        )

        // Change the edge weight from A to B, and make sure the string representation is still correct
        g['A', 'B'] = 1
        assertEquals(
            "        A    B    C    D\n" +
                    "   A null    1 null null\n" +
                    "   B null null null null\n" +
                    "   C null null null null\n" +
                    "   D null null null null",
            g.toString()
        )
    }
}