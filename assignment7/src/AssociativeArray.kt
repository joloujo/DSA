/**
 * Represents a mapping of keys to values.
 * @param K the type of the keys
 * @param V the type of the values
 */
interface AssociativeArray<K, V> {
    /**
     * Insert the mapping from the [key], to the [value].
     * If the [key] already maps to a [value], replace the mapping.
     */
    operator fun set(key: K, value: V)

    /**
     * @return true if the [key] in the associative array
     */
    operator fun contains(key: K): Boolean

    /**
     * @return the value associated with the [key] or null if it doesn't exist
     */
    operator fun get(key: K): V?

    /**
     * Remove the [key] from the associative array
     * @param key the key to remove
     * @return true if the item was successfully removed and false if the element was not found
     */
    fun remove(key: K): Boolean

    /**
     * @return the number of elements stored in the hash table
     */
    fun size(): Int

    /**
     * @return the full list of key value pairs for the associative array
     */
    fun keyValuePairs(): List<Pair<K, V>>
}