import org.junit.jupiter.api.Assertions.*

class LinkedListTest {

    @org.junit.jupiter.api.Test
    fun pushFront() {
        val l = LinkedList<Int>()

        l.pushFront(1) // 1
        // make sure the first and last are both populated
        assertEquals(l.peekFront(), l.peekBack())
        l.pushFront(2) // 2, 1
        l.pushFront(3) // 3, 2, 1

        assertEquals(3, l.peekFront())
        assertEquals(1, l.peekBack())
    }

    @org.junit.jupiter.api.Test
    fun pushBack() {
        val l = LinkedList<Int>()

        l.pushBack(1) // 1
        // make sure the first and last are both populated
        assertEquals(l.peekFront(), l.peekBack())
        l.pushBack(2) // 1, 2
        l.pushBack(3) // 1, 2, 3

        assertEquals(1, l.peekFront())
        assertEquals(3, l.peekBack())
    }

    @org.junit.jupiter.api.Test
    fun popFront() {
        val l = LinkedList<Int>()

        l.pushBack(1) // 1
        l.pushBack(2) // 1, 2
        l.pushBack(3) // 1, 2, 3

        assertEquals(1, l.popFront()) // 2, 3
        assertEquals(2, l.peekFront())
        assertEquals(3, l.peekBack())
    }

    @org.junit.jupiter.api.Test
    fun popBack() {
        val l = LinkedList<Int>()

        l.pushBack(1) // 1
        l.pushBack(2) // 1, 2
        l.pushBack(3) // 1, 2, 3

        assertEquals(3, l.popBack()) // 1, 2
        assertEquals(1, l.peekFront())
        assertEquals(2, l.peekBack())
    }

    @org.junit.jupiter.api.Test
    fun peek() {
        val l = LinkedList<Int>()

        l.pushBack(1) // 1
        l.pushBack(2) // 1, 2
        l.pushBack(3) // 1, 2, 3

        assertEquals(1, l.peekFront())
        assertEquals(3, l.peekBack())
    }

    @org.junit.jupiter.api.Test
    fun isEmpty() {
        val l = LinkedList<Int>()
        assertEquals(true, l.isEmpty())

        // test each combination of pushing to and popping from the front and back to make sure each combination is
        // handled correctly

        l.pushFront(1) // 1
        assertEquals(false, l.isEmpty())
        l.popFront() // empty
        assertEquals(true, l.isEmpty())

        l.pushBack(1) // 1
        assertEquals(false, l.isEmpty())
        l.popFront() // empty
        assertEquals(true, l.isEmpty())

        l.pushFront(1) // 1
        assertEquals(false, l.isEmpty())
        l.popBack() // empty
        assertEquals(true, l.isEmpty())

        l.pushBack(1) // 1
        assertEquals(false, l.isEmpty())
        l.popBack() // empty
        assertEquals(true, l.isEmpty())
    }
}