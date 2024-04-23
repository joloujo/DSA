/**
 * Sorts the items in a [list] from the [start] index to the [end] index using quick sort. Sorts in place.
 * @param list the list to be sorted
 * @param start the index to start sorting from
 * @param end the index to end sorting at
 */
fun <T : Comparable<T>> quickSort(list: MutableList<T>, start: Int, end: Int) {
    // If the length of the section to sort is 1 or 2, it is trivial
    when (end - start) {
        // If there is only 1 value, it is sorted
        0 -> {
            return
        }
        // If there are two values, swap them if they are out of order
        1 -> {
            if (list[start] > list[end]) {
                swap(list, start, end)
            }
            return
        }
    }

    // The pivot starts at the start
    var pivotFinalIndex = start

    // for every value that isn't the pivot
    for (i in start+1..end) {
        // if the value is less than the pivot, swap it to the bottom half
        if (list[i] < list[start]) {
            swap(list, i, pivotFinalIndex+1)
            pivotFinalIndex++
        }
    }

    // swap the pivot into place
    swap(list, start, pivotFinalIndex)

    // If there is more than one value less than the pivot, those values need to be sorted
    if (pivotFinalIndex-start > 1) {
        quickSort(list, start, pivotFinalIndex-1)
    }
    // If there is more than one value greater than the pivot, those values need to be sorted
    if (end-pivotFinalIndex > 1) {
        quickSort(list, pivotFinalIndex+1, end)
    }
}

/**
 * Sorts a [list] using quick sort. Sorts in place.
 * @param list the list to be sorted
 */
fun <T : Comparable<T>> quickSort(list: MutableList<T>) {
    val start = 0
    val end = list.size - 1
    quickSort(list, start, end)
}
