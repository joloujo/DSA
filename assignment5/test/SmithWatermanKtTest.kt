import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class SmithWatermanKtTest {

    /**
     * Test the [smithWaterman] function
     */
    @Test
    fun smithWaterman() {
        // Test sequence alignment from Wikipedia (https://en.wikipedia.org/wiki/Smith%E2%80%93Waterman_algorithm)
        assertEquals(
            Pair("GTTGAC".toMatchList(), "GTT-AC".toMatchList()),
            smithWaterman("GGTTGACTA", "TGTTACGG", {a -> 2 * a}, {a, b -> if (a == b) 3 else -3})
        )
        // Test sequence alignment from ResearchGate by Dzmitry Razmyslovich
        // (https://www.researchgate.net/figure/An-example-of-an-alignment-by-Smith-Waterman-algorithm_fig2_278675646)
        assertEquals(
            Pair("AGCACAC-A".toMatchList(), "A-CACACTA".toMatchList()),
            smithWaterman("AGCACACA", "ACACACTA", {a ->  a}, {a, b -> if (a == b) 2 else -1})
        )
    }

    /**
     * Convert a string into a list of characters, turning dashes into nulls
     * @return the string as a list of characters
     */
    private fun String.toMatchList(): List<Char?> {
        return this.toCharArray().map { if (it == '-') null else it }
    }
}