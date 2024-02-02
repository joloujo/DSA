class MyMutableIntList {
    private var arr = Array(1) { 0 }
    private var size = 0

    /**
     * Add [element] to the end of the list
     */
    fun add(element: Int) {
        when (size) {
            0 -> {
                arr = Array(1) {if (it < size) arr[it] else 0}
            }
            arr.size -> {
                arr = Array(size * 2) { if (it < size) arr[it] else 0 }
            }
        }
        arr[size] = element
        size++
    }

    /**
     * Remove all elements from the list
     */
    fun clear() {
        arr = Array(0) { 0 }
        size = 1
    }

    /*
     * @return the size of the list
     */
    fun size(): Int {
        return size
    }

    /**
     * @param index the index to return
     * @return the element at [index]
     */
    operator fun get(index: Int): Int {
        return arr[index]
    }

    /**
     * Store [value] at position [index]
     * @param index the index to set
     * @param value to store at [index]
     */
    operator fun set(index: Int, value: Int) {
        arr[index] = value
    }
}