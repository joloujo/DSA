class MyAssociativeArray<K, V> : AssociativeArray<K, V> {
    /** The number of buckets */
    private var n = goodPrimes[0]
    /** The underlying data structure */
    private var buckets: MutableList<MutableList<Pair<K, V>>> = MutableList(n) { mutableListOf() }
    /** The number of key value pairs */
    private var size = 0
    /** The threshold of the average number of key value pairs per bucket for rehashing */
    private val loadFactor = 3

    override operator fun set(key: K, value: V) {
        // Find bucket
        val hash = key.hashCode()
        val bucket = hash.mod(n)

        // Remove duplicate key if it exists
        remove(key) // reduces size if removed,
        size += 1 // so it needs to be added back no matter what (if removed, re-add size, if not found, increase size)

        // Rehash if necessary
        if (size >= n * loadFactor) rehash()

        // Add the new key value pair to the correct bucket
        buckets[bucket].add(Pair(key, value))
    }

    override fun contains(key: K): Boolean {
        // Find bucket
        val hash = key.hashCode()
        val bucket = hash.mod(n)

        // Check if the bucket contains the key
        return buckets[bucket].any() { it.first == key }
    }

    override fun get(key: K): V? {
        // Find bucket
        val hash = key.hashCode()
        val bucket = hash.mod(n)

        // Get the value if it exists
        buckets[bucket].forEach() { if (it.first == key) return it.second }
        return null
    }

    override fun remove(key: K): Boolean {
        // Find bucket
        val hash = key.hashCode()
        val bucket = hash.mod(n)

        // Try to find the key value pair, and if it exists, remove it
        buckets[bucket].forEach() {
            if (it.first == key) {
                buckets[bucket].remove(it)
                size -= 1
                return true
            }
        }

        // Return false if nothing was found
        return false
    }

    override fun size(): Int {
        return size
    }

    override fun keyValuePairs(): List<Pair<K, V>> {
        // Set up the return list
        val pairs: MutableList<Pair<K, V>> = mutableListOf()

        // Add all the kay value pairs from all the buckets
        buckets.forEach { pairs.addAll(it) }

        return pairs
    }

    /**
     * Rehashes the associative array to be a new size for faster performance. Chooses a new number [n] of [buckets] to
     * use so that [n] is as small as possible, but [n] * [loadFactor] is larger than the [size] of the associative
     * array. Takes all the elements previously in the associative array and adds them again with updated hashes.
     */
    private fun rehash() {
        // store the current values, so they can be added back later
        val pairs = keyValuePairs()

        // Find the lowest 'good' prime where the prime times the loadFactor is greater than the number of elements in
        // the associative array
        for (prime in goodPrimes) {
            if (size < prime * loadFactor) {
                n = prime
                break
            }
        }

        // Remake the underlying data structure so there are the correct number of buckets
        buckets = MutableList(n) { mutableListOf() }

        // Add all the values back into the associative array
        for (pair in pairs) {
            set(pair.first, pair.second)
        }
    }
}

/**
 * A list of 'good' primes for hash tables from [planetmath.org](https://planetmath.org/goodhashtableprimes)
 *
 * They have the properties that:
 * 1. each number in the list is prime
 * 2. each number is slightly less than twice the size of the previous
 * 3. each number is as far as possible from the nearest two powers of two
 */
private val goodPrimes = listOf(
    53,
    97,
    193,
    389,
    769,
    1543,
    3079,
    6151,
    12289,
    24593,
    49157,
    98317,
    196613,
    393241,
    786433,
    1572869,
    3145739,
    6291469,
    12582917,
    25165843,
    50331653,
    100663319,
    201326611,
    402653189,
    805306457,
    1610612741,
)