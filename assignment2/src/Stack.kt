interface Stack<T> {
    /**
     * Add [data] to the top of the stack
     */
    fun push(data: T)
    /**
     * Remove the element at the top of the stack.  If the stack is empty, it remains unchanged.
     * @return the value at the top of the stack or null if none exists
     */
    fun pop(): T?
    /**
     * @return the value on the top of the stack or null if none exists
     */
    fun peek(): T?
    /**
     * @return true if the stack is empty and false otherwise
     */
    fun isEmpty(): Boolean
}

class StackFromScratch<T>: Stack<T> {
    private var top: Node<T>? = null

    override fun push(data: T) {
        // make a new top node that references the old top node
        top = Node<T>(data, next = top)
    }

    override fun pop(): T? {
        // get the value of the top node
        val data = peek()
        // make the top the next node in the stack
        top = top?.next
        // return the value of the top node
        return data
    }

    override fun peek(): T? {
        // return the value of the top node
        return top?.data
    }

    override fun isEmpty(): Boolean {
        // if the top is null, there are no items in the stack
        return top == null
    }
}

class StackLL<T>: Stack<T> {
    private val list = LinkedList<T>()

    override fun push(data: T) {
        list.pushFront(data)
    }

    override fun pop(): T? {
        return list.popFront()
    }

    override fun peek(): T? {
        return list.peekFront()
    }

    override fun isEmpty(): Boolean {
        return list.isEmpty()
    }

}