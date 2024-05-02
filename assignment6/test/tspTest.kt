import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*

class tspTest {
    @Test
    fun tspBruteForce() {
        val g = MutableGraph<Char>()

        g.addAll('A', 'B', 'C')

        g['A', 'B'] = 1
        g['B', 'C'] = 2
        g['C', 'A'] = 3
        g['A', 'C'] = 4
        g['B', 'A'] = 5

        val (bestPath, minWeight) = tspBruteForce(g)
        println("BEST:\n$bestPath | $minWeight")
    }
}