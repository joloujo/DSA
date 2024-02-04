/**
 * A piece of [data] with a pointer to the [next] Node
 */
class Node<T> (
    val data: T,
    var next: Node<T>? = null
)