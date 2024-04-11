/**
 * A binary search tree of type [T] that uses the red-black tree invariants to self-balance
 */
class RBTree<T : Comparable<T>> {
    private var root: RBNode<T>? = null

    /**
     * Rotates the tree about the given [node]'s parent. Because a node can only have one parent, the rotation direction
     * can be automatically detected.
     * @param node the node to rotate about
     */
    private fun rotateParent(node: RBNode<T>) {
        // Make sure the node has a parent
        if (node.parent == null) throw(NullPointerException("Parent of \n$node\n in \n$this\n cannot be null"))

        // Save the grandparent and what side of it this node's parent is on to update the pointer later
        val grandparent = node.parent!!.parent
        val parentSide = if (grandparent == null) null else {
            if (node.parent == grandparent[LEFT]) LEFT else RIGHT
        }

        // Determine which side of the parent this node is on
        val side = if (node == node.parent!![LEFT]) LEFT else RIGHT

        // Rotate the tree towards the parent
        node.parent!![side] = node[!side]
        node[!side] = node.parent

        // Update the grandparent's pointer
        if (grandparent == null) {
            // If the parent was the root, make the node the root and update the node's parent to represent being the
            // root
            node.parent = null
            root = node
        } else {
            // If the root had a grandparent, make it point to the node on the correct side.
            grandparent[parentSide!!] = node
        }
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

        // Resolve any issues so the invariants are followed, starting with the inserted node
        resolve(node[dir]!!)
    }

    /**
     * Traverses up the tree, starting with the given [node], making sure the invariants are followed.
     * @param node the node to resolve from
     */
    private fun resolve(node: RBNode<T>) {
        // If the node is the root, return
        val parent = node.parent ?: return

        // If the parent is black, invariants are followed
        if (parent.color == BLACK) return

        // Because the parent is red, the parent isn't the root, there is necessarily a grandparent
        val grandparent = parent.parent!!
        val parentSide = if (parent == grandparent[LEFT]) LEFT else RIGHT

        // Get the parent's sibling
        val uncle = grandparent[!parentSide]

        // If the uncle is red
        if (uncle != null && uncle.color == RED) {
            // Flip the color of the parent, it's sibling, and the grandparent.
            parent.color = BLACK
            uncle.color = BLACK
            grandparent.color = RED

            // The next node that could be wrong is the grandparent
            resolve(grandparent)
        }
        else {
            // Keep track of the next node
            var next = node

            // If the node, it's parent, and it's grandparent form a triangle, rotate to they form a line
            if (node == parent[!parentSide]) {
                next = parent
                rotateParent(node)
            }

            // The node, it's parent, and it's grandparent form a line, rotate the problem up the tree
            next.parent!!.color = BLACK
            next.parent!!.parent!!.color = RED
            rotateParent(next.parent!!)

            // Resolve the next node that could be wrong
            resolve(next)
        }

        root!!.color = BLACK
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