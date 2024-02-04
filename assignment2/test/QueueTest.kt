import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class QueueTest{

    @Test
    fun testQueues() {
        // test both queue implementations
        testOneQueue(QueueFromScratch())
        testOneQueue(QueueLL())
    }

    private fun testOneQueue(queue: Queue<Int>) {
        // make sure the queue starts empty
        assertEquals(true, queue.isEmpty())

        // enqueue increasing numbers to the queue
        for (i in 1..10) {
            queue.enqueue(i)
        }

        // the queue should no longer be empty
        assertEquals(false, queue.isEmpty())

        // make sure that they are dequeued in the same order as they were enqueued (FIFO)
        for (i in 1..10) {
            assertEquals(i, queue.peek())
            assertEquals(i, queue.dequeue())
        }

        // the queue should now be empty
        assertEquals(true, queue.isEmpty())
    }
}