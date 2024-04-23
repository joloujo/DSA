import kotlin.math.*
import java.util.ArrayDeque

/**
 * Sorts a [list] using radix sort. Sorts in place.
 * @param list the list to be sorted
 */
fun <T : Number> radixSort(list: MutableList<T>) {
    // Set up a bucket for each digit
    val buckets = (0..9).toList().associateWith { ArrayDeque<T>() }

    // Figure out the order of magnitude of the smallest and largest digits
    var minDigit = Double.POSITIVE_INFINITY.toInt()
    var maxDigit = Double.NEGATIVE_INFINITY.toInt()

    for (number in list) {
        val (largeDigit, smallDigit) = digitRange(number)
        maxDigit = max(maxDigit, largeDigit)
        minDigit = min(minDigit, smallDigit)
    }

    // For each digit
    for (mag in minDigit..maxDigit) {
        // For each number
        for (number in list) {
            // add the number to the right bucket
            val digit = digitOf(number, mag)
            buckets[digit]!!.add(number)
        }

        // Clear the list so it can be repopulated
        list.clear()

        // Add the numbers back into the list, it digit order
        for (bucket in buckets.values) {
            while (bucket.isNotEmpty()) {
                list.add(bucket.remove())
            }
        }
    }
}

/**
 * The number 0-9 at the [digit] place in the given positive [number].
 * @param number the number to get the digit from
 * @param digit the digit to get, based on order of magnitude 10^digit. This means, for example digit 0 is the ones
 * digit, digit 2 is the 100s digit, and digit -3 is the 1000ths digit.
 * @return the number 0-9 at the [digit] place in the given [number].
 */
fun digitOf(number: Number, digit: Int): Int {
    // Ensure the given number is positive
    if (number.toFloat() < 0) {
        throw IllegalArgumentException("Number must be positive")
    }

    // Cut off at desired digit
    val upToDesired = floor(number.toDouble() / 10.0.pow(digit)).toInt()

    // Cut off just before desired digit (multiply by 10 so digits line up)
    val oneBeforeDesired = floor(number.toDouble() / 10.0.pow(digit + 1)).toInt() * 10

    // The difference is the digit we need
    return upToDesired - oneBeforeDesired
}

/**
 * The order of magnitude of the largest and smallest magnitude digits in a given positive [number]
 * @param number to determine the magnitude bounds of
 * @return a pair of doubles representing the highest and lowest non-zero digit magnitude, based on order of magnitude
 * 10^digit. For example, 3 would return (0, 0), 10 would return (1, 1), and 300.1 would return (2, -1).
 */
fun digitRange(number: Number): Pair<Int, Int> {
    // Ensure the given number is positive
    if (number.toDouble() < 0) {
        throw IllegalArgumentException("Number must be positive and non-zero")
    } else if (number == 0) {
        return Pair(0, 0)
    }

    // The largest magnitude non-zero digit can be found using log 10
    val largeDigit = floor(log10(number.toDouble())).toInt()

    // We need to iterate over the digits, starting with the largest magnitude non-zero digit, to find the smallest
    // magnitude non-zero digit
    var smallDigit = largeDigit
    // Make the "digit of interest" in the ones place
    var scaledNumber = number.toDouble() / 10.0.pow(smallDigit)
    // Keep shifting until the number is whole, meaning that we reached the last non-zero digit.
    while (abs(round(scaledNumber) - scaledNumber) > 10.0.pow(-10)) {
        scaledNumber *= 10
        smallDigit--
    }

    return Pair(largeDigit, smallDigit)
}