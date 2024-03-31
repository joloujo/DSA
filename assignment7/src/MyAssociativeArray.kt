class MyAssociativeArray<K, V> : AssociativeArray<K, V> {
    private var n = goodPrimes[0]
    private var buckets: MutableList<MutableList<Pair<K, V>>> = MutableList(n) { mutableListOf() }
    private var size = 0
    private val loadFactor = 3

    override operator fun set(key: K, value: V) {
        val hash = key.hashCode()
        val bucket = hash.mod(n)

        remove(key) // reduces size if removed,
        size += 1 // so it needs to be added back no matter what

        if (size >= n * loadFactor) rehash()

        buckets[bucket].add(Pair(key, value))
    }

    override fun contains(key: K): Boolean {
        val hash = key.hashCode()
        val bucket = hash.mod(n)

        return buckets[bucket].any() { it.first == key }
    }

    override fun get(key: K): V? {
        val hash = key.hashCode()
        val bucket = hash.mod(n)

        buckets[bucket].forEach() { if (it.first == key) return it.second }
        return null
    }

    override fun remove(key: K): Boolean {
        val hash = key.hashCode()
        val bucket = hash.mod(n)

        buckets[bucket].forEach() {
            if (it.first == key) {
                buckets[bucket].remove(it)
                size -= 1
                return true
            }
        }

        return false
    }

    override fun size(): Int {
        return size
    }

    override fun keyValuePairs(): List<Pair<K, V>> {
        val pairs: MutableList<Pair<K, V>> = mutableListOf()
        buckets.forEach { pairs.addAll(it) }
        return pairs
    }

    private fun rehash() {
        val pairs = keyValuePairs()

        for (prime in goodPrimes) {
            if (size < prime * loadFactor) {
                n = prime
                break
            }
        }

        buckets = MutableList(n) { mutableListOf() }

        for (pair in pairs) {
            set(pair.first, pair.second)
        }
    }
}

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