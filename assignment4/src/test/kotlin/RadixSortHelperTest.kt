import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

class RadixSortHelperTest {

    @Test
    fun digitOf() {
        // Make sure that getting digits works
        assertEquals(digitOf(3210.123, -3), 3)
        assertEquals(digitOf(3210.123, -2), 2)
        assertEquals(digitOf(3210.123, -1), 1)
        assertEquals(digitOf(3210.123, 0), 0)
        assertEquals(digitOf(3210.123, 1), 1)
        assertEquals(digitOf(3210.123, 2), 2)
        assertEquals(digitOf(3210.123, 3), 3)

        // Make sure that getting out of range digits works
        assertEquals(digitOf(36, -2), 0)
        assertEquals(digitOf(36, 3), 0)

        // Make sure negative numbers are not allowed
        org.junit.jupiter.api.assertThrows<IllegalArgumentException> {
            digitOf(-36, 1)
        }
    }

    @Test
    fun digitRange() {
        // Make sure that the right number of digits is returned
        assertEquals(digitRange(0), Pair(0, 0))
        assertEquals(digitRange(1), Pair(0, 0))
        assertEquals(digitRange(10), Pair(1, 1))
        assertEquals(digitRange(11), Pair(1, 0))
        assertEquals(digitRange(301.1), Pair(2, -1))
        assertEquals(digitRange(501), Pair(2, 0))

        // Make sure negative numbers are not allowed
        org.junit.jupiter.api.assertThrows<IllegalArgumentException> {
            digitRange(-1)
        }
    }
}