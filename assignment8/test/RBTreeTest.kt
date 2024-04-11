import org.junit.jupiter.api.Assertions.*
import kotlin.random.Random

class RBTreeTest {
    /**
     * Make sure that the red-black tree invariants are followed whenever an element is added
     */
    @org.junit.jupiter.api.Test
    fun add() {
        // Test 100 times
        for (i in 1..100) {
            // Create a random list of ints and a red-black tree to put them in
            val elements = List(100) { Random.nextInt(0, 1000) }
            val tree = RBTree<Int>()

            // Insert the elements into the tree one at a time, making sure the invariants are followed ever time
            elements.map { element ->
                tree.add(element)
                assertTrue(tree.followsInvariant())
            }
        }
    }

    /**
     * Make sure that the red-black tree contains all the added elements and not an element that wasn't added
     */
    @org.junit.jupiter.api.Test
    fun contains() {
        // Create a list of elements from 1-9, without 5
        val elements = listOf(1, 2, 3, 4, 6, 7, 8, 9)

        // Make a red-black tree with those elements
        val tree = rbTreeOf(elements)

        // Make sure the red-black tree contains elements 1-9, but not 5
        elements.map {assertTrue(tree.contains(it))}
        assertFalse(tree.contains(5))
    }
}
