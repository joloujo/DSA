import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll

class MatrixTest {
    private val m1 = matrixOf(listOf(
        listOf(1.0, 2.0, 3.0),
        listOf(4.0, 5.0, 6.0)
    ))
    private val m2 =matrixOf(listOf(
        listOf(7.0, 8.0),
        listOf(9.0, 10.0),
        listOf(11.0, 12.0)
    ))
    private val m3 = matrixOf(listOf(
        listOf(58.0, 64.0),
        listOf(139.0, 154.0)
    ))

    private val m4 = matrixOf(listOf(
        listOf(3.0, 4.0, 2.0),
    ))
    private val m5 = matrixOf(listOf(
        listOf(13.0, 9.0, 7.0, 15.0),
        listOf(8.0, 7.0, 4.0, 6.0),
        listOf(6.0, 4.0, 0.0, 3.0)
    ))
    private val m6 = matrixOf(listOf(
        listOf(83.0, 63.0, 37.0, 75.0)
    ))

    private val m7 = matrixOf(listOf(
        listOf(7.0, 4.0),
        listOf(1.0, 6.0)
    ))
    private val m8 = matrixOf(listOf(
        listOf(65.0, 68.0),
        listOf(140.0, 160.0)
    ))
    private val m9 = matrixOf(listOf(
        listOf(51.0, 60.0),
        listOf(138.0, 148.0)
    ))

    private val m10 = matrixOf(listOf(
        listOf(4.0, 2.0, 8.0, 5.0),
        listOf(3.0, 7.0, 5.0, 4.0),
        listOf(1.0, 0.0, 3.0, 6.0),
        listOf(9.0, 5.0, 7.0, 8.0)
    ))
    private val m11 = matrixOf(listOf(
        listOf(2.0, 4.0, 7.0, 7.0),
        listOf(5.0, 7.0, 3.0, 4.0),
        listOf(1.0, 2.0, 6.0, 9.0),
        listOf(3.0, 4.0, 1.0, 2.0)
    ))

    private val m12 = matrixOf(listOf(
        listOf(2.0, 4.0),
        listOf(5.0, 7.0)
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
    fun get() {
        assertEquals(m12, m11[0..1, 0..1])
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