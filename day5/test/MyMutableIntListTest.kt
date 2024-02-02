import org.junit.jupiter.api.Assertions.*

class MyMutableIntListTest {

    @org.junit.jupiter.api.Test
    fun add() {
        val list = MyMutableIntList()

        for (i in 1..100) {
            list.add(i)
        }
    }

    @org.junit.jupiter.api.Test
    fun clear() {
        val list = MyMutableIntList()

        for (i in 1..100) {
            list.add(i)
        }

        list.clear()
        assertEquals(list.size(), 1)
    }

    @org.junit.jupiter.api.Test
    fun size() {
        val list = MyMutableIntList()

        for (i in 1..100) {
            list.add(i)
        }

        assertEquals(100, list.size())
    }

    @org.junit.jupiter.api.Test
    fun get() {
        val list = MyMutableIntList()

        for (i in 0..100) {
            list.add(i)
        }

        for (i in 0..100) {
            assertEquals(i, list[i])
        }
    }

    @org.junit.jupiter.api.Test
    fun set() {
        val list = MyMutableIntList()

        for (i in 0..100) {
            list.add(i)
        }

        for (i in 0..100) {
            list[i] = 100-i
        }

        for (i in 0..100) {
            assertEquals(100-i, list[i])
        }
    }
}