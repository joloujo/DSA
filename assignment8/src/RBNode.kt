/**
 * One node of a red-black tree
 * @param element the element to store in this node
 * @param parent the parent node of this node
 * @param color the color of this node, defaults to red
 */
class RBNode<T : Comparable<T>>(val element: T, var parent: RBNode<T>?, var color: Boolean = RED) {
    private var left: RBNode<T>? = null
    private var right: RBNode<T>? = null

    /**
     * Get the left or right child, based on the given [direction]
     * @param direction the child to get
     * @return the child of the given [direction]
     */
    operator fun get(direction: Boolean): RBNode<T>? {
        return if (direction == LEFT) left else right
    }

    /**
     * Set the left or right child, based on the given [direction], to the given [node]
     * @param direction the child to set
     * @param node the node to set the child to
     */
    operator fun set(direction: Boolean, node: RBNode<T>?) {
        if (direction == LEFT) {
            left = node
        } else {
            right = node
        }

        node?.parent = this
    }

    /**
     * Set the left or right child, based on the given [direction], to a new node with the given [element]
     * @param direction the child to set
     * @param element to create the new node with
     */
    operator fun set(direction: Boolean, element: T) {
        // Make a new (by default) red node with the given element
        val node = RBNode(element, this)
        set(direction, node)
    }

    /**
     * Checks whether the node of the red-black tree follows the red-black tree invariants
     * @return A [Pair] of whether the red-black tree follows the invariants and the black height of the tree from this
     * node
     */
    fun followsInvariant(): Pair<Boolean, Int> {
        // If the node is red, then check if both children are black (or null, which is implicitly black)
        val inv3 = if (this.color == RED) {
            (this[LEFT] == null || this[LEFT]!!.color == BLACK) && (this[RIGHT] == null || this[RIGHT]!!.color == BLACK)
        } else true

        // Check whether each child follows the invariants, and get their black heights (null is implicitly black)
        val (leftFollows, leftHeight) = if (this[LEFT] != null) this[LEFT]!!.followsInvariant() else Pair(true, 1)
        val (rightFollows, rightHeight) = if (this[RIGHT] != null) this[RIGHT]!!.followsInvariant() else Pair(true, 1)

        // Check if the black height is the same for all paths
        val inv4 = leftHeight == rightHeight

        // Compute the black height of this node
        val height = leftHeight + if (this.color == BLACK) 1 else 0

        // Check to make sure that the invariants are followed for this node and its children
        return Pair(inv3 && inv4 && leftFollows && rightFollows, height)
    }

    /**
     * Convert the node and all its children to a string, with text coloring
     * @return the node and all its children as a string
     */
    override fun toString(): String {
        // The tab size, or number of spaces in each indentation
        val tabSize = 2

        // Make a new branch for each non-null child
        val leftString = if (left != null) {'\n' + left.toString().tab(tabSize)} else ""
        val rightString = if (right != null) {right.toString().tab(tabSize) + '\n'} else ""

        // color the element string red if the node is read
        val elementString = if (color == RED) element.toString().red() else element.toString()

        // return the combined string
        return rightString + elementString + leftString
    }
}