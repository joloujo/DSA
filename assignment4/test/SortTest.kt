import org.junit.jupiter.api.Assertions.*
import kotlin.random.Random

class SortTest {
    /**
     * Tests a specific sorting algorithm [sort]
     * @param sort the sorting algorithm to be tested
     */
    private fun testSort(sort: (list: MutableList<Int>) -> Unit) {
        for (i in 1..10) {
            val randomList = MutableList(10) { Random.nextInt(0, 100) }
            sort(randomList)
            assertTrue(sorted(randomList))
        }
    }

    @org.junit.jupiter.api.Test
    fun testSorts() {
        testSort(::quickSort)
    }

    /**
     * Checks to see if a given [list] is sorted
     * @param list the list to be checked
     */
    private fun <T : Comparable<T>> sorted(list: List<T>): Boolean {
        for (i in 0..list.size-2) {
            if (list[i] > list[i+1]) {
                return false
            }
        }
        return true
    }

    @org.junit.jupiter.api.Test
    fun testSorted() {
        assertTrue(sorted(listOf(1, 2, 3)))
        assertTrue(sorted(listOf(1, 10, 100)))
        assertTrue(sorted(listOf(1, 1, 1)))
        assertFalse(sorted(listOf(3, 2, 1)))
        assertFalse(sorted(listOf(1, 3, 2)))
        assertFalse(sorted(listOf(2, 1, 3)))
    }
}