import org.junit.jupiter.api.Assertions.*

class GraphTest {

    @org.junit.jupiter.api.Test
    fun testGraph() {
        val g = Graph<Char>()

        g.addVertex('A')
        g.addVertex('B')
        g.addVertex('C')
        g.addVertex('D')
        g.addVertex('E')

        g.addEdge('A', 'B')
        g.addEdge('A', 'C')
        g.addEdge('C', 'B')
        g.addEdge('B', 'D')
        g.addEdge('C', 'D')

        // A → C   E
        // ↓ ↙ ↓
        // B → D

        assertEquals(false, g.reachableBFS('A', 'E'))
        assertEquals(false, g.reachableDFS('A', 'E'))

        g.addEdge('C', 'E')

        // A → C → E
        // ↓ ↙ ↓
        // B → D

        assertEquals(true, g.reachableBFS('A', 'E')) // A B C D E
        assertEquals(true, g.reachableDFS('A', 'E')) // A B D C E

        g.clear()
    }
}