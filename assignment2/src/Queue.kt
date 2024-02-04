interface Queue<T> {
    /**
     * Add [data] to the end of the queue.
     */
    fun enqueue(data: T)
    /**
     * Remove the element at the front of the queue.  If the queue is empty, it remains unchanged.
     * @return the value at the front of the queue or null if none exists
     */
    fun dequeue(): T?
    /**
     * @return the value at the front of the queue or null if none exists
     */
    fun peek(): T?
    /**
     * @return true if the queue is empty and false otherwise
     */
    fun isEmpty(): Boolean
}

/**
 * A queue implemented from scratch, using the [Node] class
 */
class QueueFromScratch<T>: Queue<T> {
    private var front: Node<T>? = null
    private var end: Node<T>? = null

    override fun enqueue(data: T) {
        if (isEmpty()) {
            // if the queue is empty, make a new node and make it the front and back
            front = Node(data)
            end = front
        } else {
            // otherwise, make a new node, add it to the end, and update the end reference
            end!!.next = Node(data)
            end = end?.next
        }

    }

    override fun dequeue(): T? {
        // get the value of the front node
        val data = peek()
        // make the front the next node in the queue
        front = front?.next
        // return the value of the first node
        return data
    }

    override fun peek(): T? {
        // return the value of the first node
        return front?.data
    }

    override fun isEmpty(): Boolean {
        // if the front is null, there are no items in the queue
        return front == null
    }

}

/**
 * A queue implemented using the [LinkedList] class
 */
class QueueLL<T>: Queue<T> {
    private val list = LinkedList<T>()

    override fun enqueue(data: T) {
        list.pushBack(data)
    }

    override fun dequeue(): T? {
        return list.popFront()
    }

    override fun peek(): T? {
        return list.peekFront()
    }

    override fun isEmpty(): Boolean {
        return list.isEmpty()
    }
}