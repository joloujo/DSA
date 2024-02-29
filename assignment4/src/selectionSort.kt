/**
 * Sorts a [list] using selection sort. Sorts in place.
 * @param list the list to be sorted
 */
fun <T : Comparable<T>> selectionSort(list: MutableList<T>) {
    for (i in 0..<list.size) {
        // Set the lowest element to the next unsorted element in the list
        var minElement = list[i]
        var minIndex = i

        // Find the minimum unsorted element
        for (j in i+1..<list.size) {
            if (minElement > list[j]) {
                minElement = list[j]
                minIndex = j
            }
        }

        // If there is another unsorted element smaller than the current element, swap them. The [i]th element is now
        // sorted
        if (minIndex != i) {
            swap(list, i, minIndex)
        }
    }
}
