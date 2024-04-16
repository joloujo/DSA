/**
 * Sorts a [list] using merge sort. Sorts in place but uses additional data structures.
 * @param list the list to be sorted
 */
fun <T : Comparable<T>> mergeSort(list: MutableList<T>) {
    // If the length of the section to sort is 1 or 2, it is trivial
    when (list.size) {
        // If there is only 1 value, it is sorted
        1 -> {
            return
        }
        // If there are two values, swap them if they are out of order
        2 -> {
            if (list[0] > list[1]) {
                swap(list, 0, 1)
            }
            return
        }
    }

    // Divide the list into two halves. List.subList "returns a view of the specified elements range as a list. Thus, if
    // an element of the original collection changes, it also changes in the previously created sublists and vice
    // versa" so we have to make deep copies of the lists.
    /** The left half of the list. Deep copy, not a reference */
    val left = list.subList(0, list.size/2).toMutableList()
    /** The right half of the list. Deep copy, not a reference */
    val right = list.subList(list.size/2, list.size).toMutableList()

    // Sort the sublists
    mergeSort(left)
    mergeSort(right)

    // Merge the two lists
    /** The index of the next element on the right */
    var ri = 0
    /** The index of the next element on the left */
    var li = 0

    for (i in 0..<list.size) {
        // If there are no more on the right, pick the left
        if (ri == right.size) {
            list[i] = left[li]
            li++
        }
        // If there are no more on the left, pick the right
        // Now that we know that both still have elements, we can also compare the next elements
        // If the next left element is greater than the next right element, also pick the right
        else if (li == left.size || left[li] > right[ri]) {
            list[i] = right[ri]
            ri++
        }
        // Otherwise pick the left element
        else {
            list[i] = left[li]
            li++
        }
    }
}
