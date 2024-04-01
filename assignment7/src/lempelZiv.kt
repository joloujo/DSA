import kotlin.math.ceil
import kotlin.math.log2

/**
 * Compresses the given [input] binary list using Lempel-Ziv
 * @return the compressed output as a binary list
 */
fun lempelZiv(input: List<Boolean>): List<Boolean> {
    val string = input.toMutableList()
    // make the codebook and add the pair (Empty list -> 0)
    val codebook = MyAssociativeArray<List<Boolean>, Int>()
    codebook[listOf()] = 0

    // make a placeholder for the current phrase and codebook entry number
    val phrase = mutableListOf<Boolean>()
    var i = 1

    // make a place to store the encoding
    val encoding = mutableListOf<Boolean>()

    // until the entire string is encoded
    while(string.isNotEmpty()){
        // get the next bit
        val next = string.removeFirst()

        // if the new phrase is in the codebook, continue
        if (codebook.contains(phrase + next)) {
            phrase.add(next)
            continue
        }

        // otherwise, the phrase is new and needs to be added to the encoding and the codebook

        // find the code of the phrase without the new bit and convert it to a binary list
        val code = codebook[phrase]!!
        val digits = ceil(log2(i.toDouble())).toInt()

        // add the binary list to the encoding, then the new bit
        encoding.addAll(code.toBits(digits))
        encoding.add(next)

        // Used for debugging, follows the format of Peter Shor's notes
        // println("$i, ${(phrase+next).map { if (it) 'B' else 'A' }}, $code${if (next) "B" else "A"})")
        // println("${code.toBits(digits).map {if (it) 1 else 0}},${if (next) 1 else 0}")

        // add the new phrase to the codebook
        codebook[phrase + next] = i
        i++

        phrase.clear()
    }

    // if the end of the string did not line up with adding a new phrase to the codebook, some data may not have been
    // fully encoded. Encode that data if necessary.
    if (phrase.isNotEmpty()) {
        val code = codebook[phrase]!!
        val digits = ceil(log2(i.toDouble())).toInt()
        encoding.addAll(code.toBits(digits))
    }

    return encoding
}

/**
 * Converts [this] to a list of boolean values representing the integer in binary. If length is null, it will use the
 * minimum number of bits. If length is specified, it will
 */
fun Int.toBits(length: Int? = null): List<Boolean> {
    // Make sure the number can be converted to a binary list
    if (this < 0) throw ArithmeticException("This number must be positive")
    if (length != null) {
        if (length < 1 && this != 0) throw ArithmeticException("Number of bits must be at least 1 for non-zero number")
        if (this >= 1 shl length) throw ArithmeticException("Not enough bits")
    }

    // make a place to store the output
    val binary = mutableListOf<Boolean>()
    // start with the first digit
    var bit = 1
    // keep track of the total to know when to stop
    var total = 0

    // If the value is zero, make sure something actually gets output
    if (this == 0) {
        binary.addFirst(false)
    } else {
        // keep going until the number is reached
        while (total != this) {
            // bitwise and to determine if the digit of [this] should be included
            val add = this and bit
            // add true if result is non-zero (bit should be true), false otherwise
            binary.addFirst(add > 0)
            // keep track of the total
            total += add
            // Move the bit one to the left
            bit = bit shl 1
        }
    }

    // Pad if necessary
    if (length != null) {
        while (binary.size < length) binary.addFirst(false)
    }

    return binary
}

/**
 * Compresses the given [input] string using Lempel-Ziv
 * @return the compressed output as a binary list
 */
fun lempelZiv(input: String): List<Boolean> {
    val string = input.toCharArray().toMutableList()
    // make the codebook and add the pair (Empty list -> 0)
    val codebook = MyAssociativeArray<List<Char>, Int>()
    codebook[listOf()] = 0

    // make a placeholder for the current phrase and codebook entry number
    val phrase = mutableListOf<Char>()
    var i = 1

    // make a place to store the encoding
    val encoding = mutableListOf<Boolean>()

    // until the entire string is encoded
    while(string.isNotEmpty()){
        // get the next char
        val next = string.removeFirst()

        // if the new phrase is in the codebook, continue
        if (codebook.contains(phrase + next)) {
            phrase.add(next)
            continue
        }

        // otherwise, the phrase is new and needs to be added to the encoding and the codebook

        // find the code of the phrase without the new char and convert it to a binary list
        val code = codebook[phrase]!!
        val digits = ceil(log2(i.toDouble())).toInt()

        // add the binary list to the encoding, then the new char
        encoding.addAll(code.toBits(digits))
        encoding.addAll(next.toBits())

        // add the new phrase to the codebook
        codebook[phrase + next] = i
        i++

        phrase.clear()
    }

    // if the end of the string did not line up with adding a new phrase to the codebook, some data may not have been
    // fully encoded. Encode that data if necessary.
    if (phrase.isNotEmpty()) {
        val code = codebook[phrase]!!
        val digits = ceil(log2(i.toDouble())).toInt()
        encoding.addAll(code.toBits(digits))
    }

    return encoding
}

/**
 * Converts [this] to a list of eight boolean values (a byte) representing the integer in binary.
 */
fun Char.toBits(): List<Boolean> {
    return this.code.toBits(8)
}