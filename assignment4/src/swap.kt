/**
 * Swaps the element at index [i] with element at index [j] in a given [list]
 */
fun <T> swap(list: MutableList<T>, i: Int, j: Int) {
    val temp = list[i]
    list[i] = list[j]
    list[j] = temp
}
