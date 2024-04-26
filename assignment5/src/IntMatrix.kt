import java.util.*
import kotlin.math.log2

/**
 * A [Matrix] of integers, that has built in numerical operations
 * @param rows the number of rows
 * @param cols the number of columns
 */
class IntMatrix(rows: Int, cols: Int) : Matrix<Int?>(rows, cols) {

    // Use 0 as a default value to make sure there are no null values
    init {
        data.replaceAll { MutableList(cols) {0} }
    }

    /**
     * Get the value in a given [row] and [column][col]
     * @param row the row to get from
     * @param col the column to get from
     * @return the value at the given location
     */
    override operator fun get(row: Int, col: Int): Int {
        return data[row][col]!!
    }

    /**
     * Get the values in a given range of [rows] and [columns][cols]
     * @param rows the rows to get from
     * @param cols the columns to get from
     * @return An [IntMatrix] of the values in the given ranges
     */
    override operator fun get(rows: IntRange, cols: IntRange): IntMatrix {
        // Loop over all values to get and make a 2D list
        val newData = rows.map {i -> cols.map {j -> data[i][j]!!}}
        // Make a matrix from the data and return it
        return matrixOf(newData)
    }

    /**
     * Multiply this [IntMatrix] by [another][other]. Uses traditional matrix multiplication
     * @param other the [IntMatrix] to multiply by
     * @return this*[other] if the dimensions are compatible and null otherwise
     */
    private fun multiply(other: IntMatrix):IntMatrix? {
        // Make sure the sizes are compatible
        if (cols != other.rows) return null

        // store the number of values that will need to be added up for each cell
        val n = cols

        // Create an IntMatrix to store the result
        val result = IntMatrix(rows, other.cols)

        // For each row in this matrix
        for (i in 0..<rows) {
            // for each column in this matrix
            for (j in 0..<other.cols) {
                // Sum the products of each pair of values in this row and the other column and add it to the IntMatrix
                for (k in 0..<n) {
                    result[i, j] += get(i, k) * other[k, j]
                }
            }
        }

        return result
    }

    /**
     * A wrapper for the [multiply] function, so it can be used as an operator
     * @param other the [IntMatrix] to multiply by
     * @return this*[other] if the dimensions are compatible and all entries are filled and null otherwise
     */
    operator fun times(other: IntMatrix): IntMatrix? {
        return multiply(other)
    }

    /**
     * Performs a [function] to each pair of entries in this [IntMatrix] and the given [other] [IntMatrix]
     * @param other the other [IntMatrix] to perform operations with
     * @param function the function to perform
     * @return the [IntMatrix] result of the elementwise operation
     */
    private fun elementwise(other: IntMatrix, function: (Int, Int) -> Int): IntMatrix {
        // Make sure the matrices are the same size
        if (rows != other.rows || cols != other.cols) throw ArithmeticException("Matrices must be the same size")

        // Create an IntMatrix to store the result
        val result = IntMatrix(rows, cols)

        // Loop through every pair of values
        for (i in 0..<rows) {
            for (j in 0..<cols) {
                // Get the values
                val a = get(i, j)
                val b = other[i, j]

                // Perform the operation
                val value = function(a, b)

                // Store the result
                result[i, j] += value
            }
        }

        return result
    }

    /**
     * Elementwise addition
     * @param other the other [IntMatrix] to add
     * @return the sum of the two matrices
     */
    operator fun plus(other: IntMatrix): IntMatrix {
        return elementwise(other) {a, b -> a + b}
    }

    /**
     * Elementwise addition
     * @param other the other [IntMatrix] to subtract
     * @return the difference of the two matrices
     */
    operator fun minus(other: IntMatrix): IntMatrix {
        return elementwise(other) {a, b -> a - b}
    }

    /**
     * Multiply this [IntMatrix] by [another][other]. Uses Strassen's algorithm
     * @param other the [IntMatrix] to multiply by
     * @return this*[other] if the dimensions are compatible and null otherwise
     */
    fun strassenMultiply(other: IntMatrix): IntMatrix? {
        // Make sure the matrices are the same size, square, and the size is a power of 2
        if (rows != other.rows || cols != other.cols) return null
        if (rows != cols) return null
        if (log2(rows.toDouble()) % 1 != 0.0) return null

        // Create a matrix to store the values
        val result = IntMatrix(rows, rows)

        // Base case where sub-matrices are individual values
        if (rows == 2) {
            // Get values from this matrix
            val a = get(0, 0)
            val b = get(0, 1)
            val c = get(1, 0)
            val d = get(1, 1)

            // Get values from the other matrix
            val e = other[0, 0]
            val f = other[0, 1]
            val g = other[1, 0]
            val h = other[1, 1]

            // Calculate the values needed for strassen
            val p1 = a * (f - h)
            val p2 = (a + b) * h
            val p3 = (c + d) * e
            val p4 = d * (g - e)
            val p5 = (a + d) * (e + h)
            val p6 = (b - d) * (g + h)
            val p7 = (a - c) * (e + f)

            // Calculate the result values based on the strassen values
            result[0, 0] = p5 + p4 - p2 + p6
            result[0, 1] = p1 + p2
            result[1, 0] = p3 + p4
            result[1, 1] = p1 + p5 - p3 - p7
        } else {
            // determine the cut point
            val cut = rows/2

            // Get sub-matrices from this matrix
            val a = get(0..<cut, 0..<cut)
            val b = get(0..<cut, cut..<rows)
            val c = get(cut..<rows, 0..<cut)
            val d = get(cut..<rows, cut..<rows)

            // Get sub-matrices from the other matrix
            val e = other[0..<cut, 0..<cut]
            val f = other[0..<cut, cut..<rows]
            val g = other[cut..<rows, 0..<cut]
            val h = other[cut..<rows, cut..<rows]

            // Calculate the values needed for strassen
            val p1 = a.strassenMultiply((f - h))!!
            val p2 = (a + b).strassenMultiply(h)!!
            val p3 = (c + d).strassenMultiply(e)!!
            val p4 = d.strassenMultiply((g - e))!!
            val p5 = (a + d).strassenMultiply((e + h))!!
            val p6 = (b - d).strassenMultiply((g + h))!!
            val p7 = (a - c).strassenMultiply((e + f))!!

            // Calculate the result values based on the strassen values
            result[0..<cut, 0..<cut] = p5 + p4 - p2 + p6
            result[0..<cut, cut..<rows] = p1 + p2
            result[cut..<rows, 0..<cut] = p3 + p4
            result[cut..<rows, cut..<rows] = p1 + p5 - p3 - p7
        }

        return result
    }

    /**
     * The location of the maximum value in the [IntMatrix]
     * @return a [Pair] of ints representing the row and column of the maximum value
     */
    fun maxLoc(): Pair<Int, Int> {
        // initialize the max and max location
        var max = Int.MIN_VALUE
        var loc = Pair(0, 0)

        // Check every cell and keep track of the max value and it's location
        for (i in 0..<data.size) {
            val row = data[i]
            for (j in 0..<row.size) {
                val value = row[j]!!
                if (value > max) {
                    max = value
                    loc = Pair(i, j)
                }
            }
        }

        return loc
    }
}

/**
 * Make an [IntMatrix] from a list of lists of ints
 *  * @param data the data to create the [IntMatrix] from
 *  * @return the [IntMatrix]
 */
fun matrixOf(data: List<List<Int>>): IntMatrix {
    // Find the necessary number of rows and columns
    val rows = data.size
    val cols = Collections.max(data.map { it.size })

    // Create a matrix to store the values and return
    val result = IntMatrix(rows, cols)

    // Add the values
    for (i in 0..<rows) {
        for (j in 0..<cols) {
            result[i, j] = data[i][j]
        }
    }

    // Return the populated matrix
    return result
}