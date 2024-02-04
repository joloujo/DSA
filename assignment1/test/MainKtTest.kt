import org.junit.jupiter.api.Assertions.*

class MainKtTest {

    @org.junit.jupiter.api.Test
    fun diffTest() {
        val cases = mapOf(
            listOf(1, 8, 3, 14, 12) to listOf(7, -5, 11, -2),
            listOf(1, 2, 3, 4) to listOf(1, 1, 1),
            listOf(1, 1, 1) to listOf(0, 0),
        )

        cases.forEach {
            assertEquals(diff(it.key), it.value)
        }
    }

    @org.junit.jupiter.api.Test
    fun predictTest() {
        val cases = mapOf(
            listOf(0, 3, 6, 9, 12, 15) to 18,
            listOf(1, 3, 6, 10, 15, 21) to 28,
            listOf(10, 13, 16, 21, 30, 45) to 68,
        )

        cases.forEach {
            assertEquals(predict(it.key), it.value)
        }
    }

    @org.junit.jupiter.api.Test
    fun totalTest() {
        val input = "test/day9test.txt"
        val output = 114

        assertEquals(total(input), output)
    }

    @org.junit.jupiter.api.Test
    fun mainTest() {
        assertEquals(main(), Unit)
    }
}