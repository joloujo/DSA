import org.junit.jupiter.api.Assertions.*

class MyAssociativeArrayTest {

    @org.junit.jupiter.api.Test
    fun test() {
        val arr = MyAssociativeArray<Char, Int>()

        // Test basic set
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
}