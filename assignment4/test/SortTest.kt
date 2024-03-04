import org.junit.jupiter.api.Assertions.*
import kotlin.random.Random

class SortTest {
    /**
     * Tests a specific sorting algorithm [sort]
     * @param sort the sorting algorithm to be tested
     */
    private fun testSort(sort: (list: MutableList<Int>) -> Unit, allowNegatives: Boolean = true) {
        // Test 100 times
        for (i in 1..100) {
            // Generate a list with a random length 10->100 with random integers from -100->100
            // If negatives aren't allowed, use the range 0->100
            val from = if (allowNegatives) -100 else 0
            val randomList = MutableList(Random.nextInt(10, 100)) { Random.nextInt(from, 100) }

            // Try to sort it
            sort(randomList)

            // Make sure it's sorted
            assertTrue(sorted(randomList))
        }
    }

    @org.junit.jupiter.api.Test
    fun testSorts() {
        testSort(::quickSort)
        testSort(::mergeSort)
        testSort(::selectionSort)
        testSort(::radixSort, false)
    }

    /**
     * Checks to see if a given [list] is sorted
     * @param list the list to be checked
     */
    private fun <T : Comparable<T>> sorted(list: List<T>): Boolean {
        // Check every pair of values to make sure they are non-decreasing
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