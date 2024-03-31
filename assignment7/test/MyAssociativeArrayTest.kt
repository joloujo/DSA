import org.junit.jupiter.api.Assertions.*

class MyAssociativeArrayTest {

    /**
     * Test the main functions of the associative array
     */
    @org.junit.jupiter.api.Test
    fun test() {
        // Make the associative array
        val arr = MyAssociativeArray<Char, Int>()

        // Test set
        arr['A'] = 1
        arr['B'] = 2
        arr['C'] = 3

        // Check set and test get
        assertEquals(1, arr['A'])
        assertEquals(2, arr['B'])
        assertEquals(3, arr['C'])
        assertEquals(null, arr['D'])

        // Test contains
        assertTrue(arr.contains('A'))
        assertFalse(arr.contains('D'))

        // Test keyValuePairs
        assertTrue(arr.keyValuePairs().containsAll(listOf(
            Pair('A', 1),
            Pair('B', 2),
            Pair('C', 3),
        )))

        // Test size
        assertEquals(3, arr.size())

        // Test setting new value for existing key
        arr['A'] = 10
        assertEquals(10, arr['A'])
        assertTrue(arr.keyValuePairs().containsAll(listOf(
            Pair('A', 10),
            Pair('B', 2),
            Pair('C', 3),
        )))
        assertFalse(arr.keyValuePairs().contains(
            Pair('A', 1)
        ))

        // Test size after setting new value for existing key
        assertEquals(3, arr.size())

        // Test remove
        assertTrue(arr.remove('B'))
        assertFalse(arr.contains('B'))
        assertFalse(arr.remove('D'))

        // Test size after remove
        assertEquals(2, arr.size())
    }

    /**
     * Test to make sure that rehashing works correctly and automatically
     */
    @org.junit.jupiter.api.Test
    fun rehash() {
        // Make an associative array to test and a list of pairs to check against
        val arr = MyAssociativeArray<Char, Int>()
        val pairs = mutableListOf<Pair<Char, Int>>()

        // Add elements to the array and list of pairs
        // We need at least 53 (default n) * 3 (default load factor) = 159 elements (0 -> 158)
        for (i in 0..158) {
            val c = i.toChar()
            arr[c] = i
            pairs.add(Pair(c, i))
        }

        // Make sure all the pairs are in the associative array,
        assertTrue(arr.keyValuePairs().containsAll(pairs))
        // and all the associative array elements are in the pairs
        assertTrue(pairs.containsAll(arr.keyValuePairs()))
    }
}