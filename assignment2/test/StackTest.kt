import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class StackTest{

    @Test
    fun testStacks() {
        // test both stack implementations
        testOneStack(StackFromScratch())
        testOneStack(StackLL())
    }

    private fun testOneStack(stack: Stack<Int>) {
        // make sure the stack starts empty
        assertEquals(true, stack.isEmpty())

        // push increasing numbers to the stack
        for (i in 1..10) {
            stack.push(i)
        }

        // the stack should no longer be empty
        assertEquals(false, stack.isEmpty())

        // make sure that they are popped in the reverse order they were pushed (LIFO)
        for (i in 10 downTo 1) {
            assertEquals(i, stack.peek())
            assertEquals(i, stack.pop())
        }

        // the stack should now be empty
        assertEquals(true, stack.isEmpty())
    }
}