import java.util.Collections.max
import kotlin.math.log2

/**
 * A two-dimensional ordered collection of values, with a given number of [rows] and [columns][cols].
 * @param T the type of value to store
 * @param rows the number of rows
 * @param cols the number of columns
 */
open class Matrix<T>(protected val rows: Int, protected val cols: Int) {
    protected open val data: MutableList<MutableList<T?>> = MutableList(rows) { MutableList(cols) {null} }

    /**
     * Get the value in a given [row] and [column][col]
     * @param row the row to get from
     * @param col the column to get from
     * @return the value of type [T] at the given location (could be null)
     */
    open operator fun get(row: Int, col: Int): T? {
        return data[row][col]
    }

    /**
     * Get the values in a given range of [rows] and [columns][cols]
     * @param rows the rows to get from
     * @param cols the columns to get from
     * @return A [Matrix] of type [T] of the values in the given ranges (can be null)
     */
    open operator fun get(rows: IntRange, cols: IntRange): Matrix<T?> {
        // Loop over all values to get and make a 2D list
        val newData = rows.map {i -> cols.map {j -> data[i][j]}}
        // Make a matrix from the data and return it
        return matrixOf(newData)
    }

    /**
     * Set the value in a given [row] and [column][col]
     * @param row the row to set in
     * @param col the column to set in
     * @param value the value to set to
     */
    operator fun set(row: Int, col: Int, value: T?) {
        data[row][col] = value
    }

    /**
     * Set the values in a given range of [rows] and [columns][cols]
     * @param rows the rows to set in
     * @param cols the columns to set in
     * @param values the [Matrix] of values to set to
     */
    operator fun set(rows: IntRange, cols: IntRange, values: Matrix<T>) {
        // Loop over all values to set and set them
        rows.forEachIndexed {i, row ->
            cols.forEachIndexed {j, col ->
                data[row][col] = values[i, j]
            }
        }
    }

    /**
     * Better string formatting
     */
    override fun toString(): String {
        // Separate each item with commas
        val stringRows = data.map { it.joinToString(separator = ", ") }
        // Separate each row with line breaks
        return stringRows.joinToString(separator = "\n")
    }

    /**
     * Compare this [Matrix] with another object
     * @param other the object to compare to
     * @return whether the two objects are the same
     */
    override fun equals(other: Any?): Boolean {
        // If the objects are the same object, they're equal
        if (this === other) return true
        // If the objects are different classes, they're not equal
        if (javaClass != other?.javaClass) return false

        other as Matrix<*>

        // If the data is the same, then they're equal
        return data == other.data
    }

    /**
     * The hash function of the [Matrix]
     * @return the hash
     */
    override fun hashCode(): Int {
        return data.hashCode()
    }
}

/**
 * Make a [Matrix] from a list of lists
 * @param data the data to create the [Matrix] from
 * @return the [Matrix]
 */
fun <T> matrixOf(data: List<List<T>>): Matrix<T> {
    // Find the necessary number of rows and columns
    val rows = data.size
    val cols = max(data.map { it.size })

    // Create a matrix to store the values and return
    val result = Matrix<T>(rows, cols)

    // Add the values
    for (i in 0..<rows) {
        for (j in 0..<cols) {
            result[i, j] = data[i][j]
        }
    }

    // Return the populated matrix
    return result
}