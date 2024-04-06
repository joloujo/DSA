internal const val BLACK = false
internal const val RED = !BLACK
internal const val LEFT = false
internal const val RIGHT = !LEFT

/**
 * Create a [red-black tree][RBTree] from the given [collection]
 * @param collection the [Collection] to make the red-black tree from
 * @return the red-black tree
 */
fun <T : Comparable<T>> rbTreeOf(collection: Collection<T>): RBTree<T> {
    val tree = RBTree<T>()
    tree.addAll(collection)
    return tree
}

/**
 * Create a [red-black tree][RBTree] from the given [elements]
 * @param elements the elements to make the red-black tree from
 * @return the red-black tree
 */
fun <T : Comparable<T>> rbTreeOf(vararg elements: T): RBTree<T> {
    return rbTreeOf(elements.toList())
}

/**
 * Make the given string red when printed
 * @return the red string
 */
fun String.red(): String {
    return "\u001b[1;31m$this\u001b[0m"
}

/**
 * Indent the given string by the given number of [spaces]. Indents each line individually
 * @param spaces the tab size, or number of spaces in each indentation
 * @return the indented string
 */
fun String.tab(spaces: Int): String {
    // Create the tab string
    val tab = " ".repeat(spaces)

    // Build the string one line at a time, indenting each one
    var string = ""
    for (line in this.split("\n")) {
        string += "$tab$line\n"
    }

    // remove the last line break before returning
    return string.trimEnd()
}