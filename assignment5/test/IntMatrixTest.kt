import org.junit.jupiter.api.Assertions.*

class IntMatrixTest {
    // Create matrices to test with
    private val m1 = matrixOf(listOf(
        listOf(1, 2, 3),
        listOf(4, 5, 6)
    ))
    private val m2 =matrixOf(listOf(
        listOf(7, 8),
        listOf(9, 10),
        listOf(11, 12)
    ))
    private val m3 = matrixOf(listOf(
        listOf(58, 64),
        listOf(139, 154)
    ))

    private val m4 = matrixOf(listOf(
        listOf(3, 4, 2),
    ))
    private val m5 = matrixOf(listOf(
        listOf(13, 9, 7, 15),
        listOf(8, 7, 4, 6),
        listOf(6, 4, 0, 3)
    ))
    private val m6 = matrixOf(listOf(
        listOf(83, 63, 37, 75)
    ))

    private val m7 = matrixOf(listOf(
        listOf(7, 4),
        listOf(1, 6)
    ))
    private val m8 = matrixOf(listOf(
        listOf(65, 68),
        listOf(140, 160)
    ))
    private val m9 = matrixOf(listOf(
        listOf(51, 60),
        listOf(138, 148)
    ))

    private val m10 = matrixOf(listOf(
        listOf(4, 2, 8, 5),
        listOf(3, 7, 5, 4),
        listOf(1, 0, 3, 6),
        listOf(9, 5, 7, 8)
    ))
    private val m11 = matrixOf(listOf(
        listOf(2, 4, 7, 7),
        listOf(5, 7, 3, 4),
        listOf(1, 2, 6, 9),
        listOf(3, 4, 1, 2)
    ))

    private val m12 = matrixOf(listOf(
        listOf(2, 4),
        listOf(5, 7)
    ))

    /**
     * Test the [times][IntMatrix.times] method of the [IntMatrix] class.
     */
    @org.junit.jupiter.api.Test
    fun multiply() {
        // Test basic multiplication
        assertEquals(m3, m1 * m2)
        assertEquals(m6, m4 * m5)

        // Make sure null is returned with incompatible sizes
        assertNull(m1 * m4)
    }

    /**
     * Test the [plus][IntMatrix.plus] method of the [IntMatrix] class.
     */
    @org.junit.jupiter.api.Test
    fun plus() {
        // Test addition
        assertEquals(m8, m3 + m7)
    }

    /**
     * Test the [minus][IntMatrix.minus] method of the [IntMatrix] class.
     */
    @org.junit.jupiter.api.Test
    fun minus() {
        // Test subtraction
        assertEquals(m9, m3 - m7)
    }

    /**
     * Test the [strassenMultiply][IntMatrix.strassenMultiply] method of the [IntMatrix] class.
     */
    @org.junit.jupiter.api.Test
    fun strassenMultiply() {
        // Make sure strassen multiplication returns the same as traditional multiplication
        assertEquals(m3 * m7, m3.strassenMultiply(m7))
        assertEquals(m8 * m9, m8.strassenMultiply(m9))
        assertEquals(m10 * m11, m10.strassenMultiply(m11))

        // Make sure null is returned with incompatible sizes
        assertNull(m1.strassenMultiply(m2))
        assertNull(m10.strassenMultiply(m3))
    }
}