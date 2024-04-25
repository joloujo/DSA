import java.util.Collections.max
import kotlin.math.log2

open class Matrix<T>(protected val rows: Int, protected val cols: Int) {
    protected open val data: MutableList<MutableList<T?>> = MutableList(rows) { MutableList(cols) {null} }

    open operator fun get(row: Int, col: Int): T? {
        return data[row][col]
    }

    open operator fun get(rows: IntRange, cols: IntRange): Matrix<T?> {
        val newData = rows.map {i -> cols.map {j -> data[i][j]}}
        return matrixOf(newData)
    }

    operator fun set(row: Int, col: Int, value: T?) {
        data[row][col] = value
    }

    operator fun set(rows: IntRange, cols: IntRange, values: Matrix<T>) {
        rows.forEachIndexed {i, row ->
            cols.forEachIndexed {j, col ->
                data[row][col] = values[i, j]
            }
        }
    }

    override fun toString(): String {
        val stringRows = data.map { it.joinToString(separator = ", ") }
        return stringRows.joinToString(separator = "\n")
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Matrix<*>

        return data == other.data
    }

    override fun hashCode(): Int {
        return data.hashCode()
    }
}

fun <T> matrixOf(data: List<List<T>>): Matrix<T> {
    val rows = data.size
    val cols = max(data.map { it.size })

    val result = Matrix<T>(rows, cols)

    for (i in 0..<rows) {
        for (j in 0..<cols) {
            result[i, j] = data[i][j]
        }
    }

    return result
}