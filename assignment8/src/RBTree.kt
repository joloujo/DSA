/**
 * A binary search tree of type [T] that uses the red-black tree invariants to self-balance
 */
class RBTree<T : Comparable<T>> {
    private var root: RBNode<T>? = null

    /**
     * Rotates the tree about the given [node]. Automatically detects direction
     * @param node the node to rotate about
     */
    fun rotate(node: RBNode<T>) {
        // TODO()
    }

    /**
     * Adds an [element] to the red-black tree and ensures the invariant is followed afterward
     * @param element the element to add to the red-black tree
     */
    fun add(element: T) {
        // If the red-black tree is empty, make the root a new black node with the given element.
        if (root == null) {root = RBNode(element, null, BLACK); return}

        // Start at the root
        var node: RBNode<T> = root!!
        var dir: Boolean
        // Loop until you reach a leaf
        while (true) {
            dir = if (element < node.element) LEFT else RIGHT
            if (node[dir] == null) break
            node = node[dir]!!
        }

        // Make a new node at the leaf
        node[dir] = element
        node[dir]!!.color = RED

        // TODO()
    }

    /**
     * Adds multiple [elements] to the red-black tree and ensures the invariant is followed afterward
     * @param elements the [Collection] of elements to add
     */
    fun addAll(elements: Collection<T>) {
        for (value in elements) {
            add(value)
        }
    }

    /**
     * Checks whether an [element] is in the red-black tree
     * @param element the element to check for
     * @return whether the [element] is in the red-black tree
     */
    fun contains(element: T): Boolean {
        // Start at the root
        var node: RBNode<T>? = root

        // Loop until you find the element or reach a leaf
        while (node != null && node.element != element) {
            // Go left if the value is smaller, right if bigger
            node = if (element < node.element) node[LEFT] else node[RIGHT]
        }

        // return whether you found the element
        return node != null
    }

    /**
     * Checks whether the red-black tree follows the red-black tree invariants
     * @return whether the red-black tree follows the invariants
     */
    fun followsInvariant(): Boolean {
        // If the red-black tree is empty, the invariants are followed
        if (root == null) return true

        // Make sure the root is black
        val inv2 = root!!.color == BLACK

        // Check whether each child follows the invariants, and get their black heights (null is implicitly black)
        val (leftFollows, leftHeight) = if (root!![LEFT] != null) root!![LEFT]!!.followsInvariant() else Pair(true, 1)
        val (rightFollows, rightHeight) = if (root!![RIGHT] != null) root!![RIGHT]!!.followsInvariant() else Pair(true, 1)

        // Check if the black height is the same for all paths
        val inv4 = leftHeight == rightHeight

        // Check to make sure that the invariants are followed for this node and its children
        return inv2 && inv4 && leftFollows && rightFollows
    }

    /**
     * Convert the red-black tree to a string, with text coloring
     * @return the red-black tree as a string
     */
    override fun toString(): String {
        return root.toString()
    }
}