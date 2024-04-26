import org.junit.jupiter.api.Assertions.*

class MatrixTest {
    // Create matrices to test with
    private val m1 = matrixOf(listOf(
        listOf('A', 'B', 'C'),
        listOf('D', 'E', 'F'),
        listOf('G', 'H', 'I')
    ))
    private val m3 = matrixOf(listOf(
        listOf('D', 'E'),
        listOf('G', 'H')
    ))

    /**
     * Test the [get][Matrix.get] methods of the [Matrix] class.
     */
    @org.junit.jupiter.api.Test
    fun get() {
        // Test getting a single value
        assertEquals('E', m1[1, 1])
        // Test getting a range of values. Implicitly tests equals as well
        assertEquals(m3, m1[1..2, 0..1])
    }

    /**
     * Test the [set][Matrix.set] methods of the [Matrix] class.
     */
    @org.junit.jupiter.api.Test
    fun set() {
        // Set the value at (1, 1) to 'X'
        m1[1, 1] = 'X'
        // Make sure it set
        assertEquals('X', m1[1, 1])
        // Reset the value at (1, 1) to 'E'
        m1[1, 1] = 'E'
        // Make sure it reset
        assertEquals('E', m1[1, 1])
    }
}