import org.junit.jupiter.api.Assertions.*

class IntMatrixTest {
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

    @org.junit.jupiter.api.Test
    fun multiply() {
        assertEquals(m3, m1 * m2)
        assertEquals(m6, m4 * m5)
        assertNull(m1 * m4)
    }

    @org.junit.jupiter.api.Test
    fun plus() {
        assertEquals(m8, m3 + m7)
    }

    @org.junit.jupiter.api.Test
    fun minus() {
        assertEquals(m9, m3 - m7)
    }

    @org.junit.jupiter.api.Test
    fun strassenMultiply() {
        assertEquals(m3 * m7, m3.strassenMultiply(m7))
        assertEquals(m8 * m9, m8.strassenMultiply(m9))
        assertEquals(m10 * m11, m10.strassenMultiply(m11))

        assertNull(m1.strassenMultiply(m2))
        assertNull(m10.strassenMultiply(m3))
    }
}