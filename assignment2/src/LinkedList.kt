class LinkedList<T> {
    private var head: LinkedListNode<T>? = null
    private var tail: LinkedListNode<T>? = null

    /**
     * Adds the element [data] to the front of the linked list.
     */
    fun pushFront(data: T) {
        // create a new node, make it's next reference the current head, and make it the new head
        head = LinkedListNode(data, next = head)
        // update the old head's (head?.next) previous reference to be the new head
        head?.next?.prev = head
        // If the linked list was empty, then the tail is also the head
        if (tail == null) tail = head
    }

    /**
     * Adds the element [data] to the back of the linked list.
     */
    fun pushBack(data: T) {
        // create a new node, make it's prev reference the current tail, and make it the new tail
        tail = LinkedListNode(data, prev = tail)
        // update the old tail's (tail?.prev) next reference to be the new tail
        tail?.prev?.next = tail
        // If the linked list was empty, then the head is also the tail
        if (head == null) head = tail
    }

    /**
     * Removes an element from the front of the list. If the list is empty, it is unchanged.
     * @return the value at the front of the list or null if none exists
     */
    fun popFront(): T? {
        // get the value of the first node
        val data = peekFront()
        // update the head to be the second element
        head = head?.next
        // remove the reference to the node to be popped
        head?.prev = null
        // If the linked list is now empty, the tail is also null
        if (head == null) tail = null
        // return the value of the first node
        return data
    }

    /**
     * Removes an element from the back of the list. If the list is empty, it is unchanged.
     * @return the value at the back of the list or null if none exists
     */
    fun popBack(): T? {
        // get the value of the last node
        val data = peekBack()
        // update the tail to be the second to last element
        tail = tail?.prev
        // remove the reference to the node to be popped
        tail?.next = null
        // If the linked list is now empty, the head is also null
        if (tail == null) head = null
        // return the value of the last node
        return data
    }

    /**
     * @return the value at the front of the list or null if none exists
     */
    fun peekFront(): T? {
        // return the value of the first node
        return head?.data
    }

    /**
     * @return the value at the back of the list or null if none exists
     */
    fun peekBack(): T? {
        // return the value of the last node
        return tail?.data
    }

    /**
     * @return true if the list is empty and false otherwise
     */
    fun isEmpty(): Boolean {
        // If the head is empty, there are no nodes in the linked list
        return head == null
    }
}

/**
 * A class to store one piece of [data] in the linked list, as well as the references to the [next] and [prev]ious
 * elements in the linked list.
 */
private class LinkedListNode<T>(
    val data: T,
    var prev: LinkedListNode<T>? = null,
    var next: LinkedListNode<T>? = null
)