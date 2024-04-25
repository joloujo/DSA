import org.junit.jupiter.api.Assertions.*

class MatrixTest {
    private val m1 = matrixOf(listOf(
        listOf('A', 'B', 'C'),
        listOf('D', 'E', 'F'),
        listOf('G', 'H', 'I')
    ))
    private val m3 = matrixOf(listOf(
        listOf('D', 'E'),
        listOf('G', 'H')
    ))

    @org.junit.jupiter.api.Test
    fun get() {
        assertEquals('E', m1[1, 1])
        assertEquals(m3, m1[1..2, 0..1])
    }
}