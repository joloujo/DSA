import java.util.Collections.max
import kotlin.math.log2

class Matrix(private val rows: Int, private val cols: Int) {
    private val data: MutableList<MutableList<Double>> = MutableList(rows) { MutableList(cols) {0.0} }

    operator fun get(row: Int, col: Int): Double {
        return data[row][col]
    }

    operator fun get(rows: IntRange, cols: IntRange): Matrix {
        val newData = rows.map {i -> cols.map {j -> data[i][j]}}
        return matrixOf(newData)
    }

    operator fun set(row: Int, col: Int, value: Double) {
        data[row][col] = value
    }

    operator fun set(rows: IntRange, cols: IntRange, values: Matrix) {
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

        other as Matrix

        return data == other.data
    }

    override fun hashCode(): Int {
        return data.hashCode()
    }

    /**
     * Multiply this matrix by [other].
     * You can implement this either using block-based matrix multiplication or
     * traditional matrix multiplication (the kind you learn about in math
     * classes!)
     * @return this*[other] if the dimensions are compatible and null otherwise
     */
    fun multiply(other: Matrix):Matrix? {
        if (cols != other.rows) return null

        val n = cols

        val result = Matrix(rows, other.cols)

        for (i in 0..<rows) {
            for (j in 0..<other.cols) {
                for (k in 0..<n) {
                    result[i, j] += get(i, k) * other[k, j]
                }
            }
        }

        return result
    }

    operator fun times(other: Matrix): Matrix? {
        return multiply(other)
    }

    fun elementwise(other: Matrix, function: (Double, Double) -> Double): Matrix {
        if (rows != other.rows || cols != other.cols) throw ArithmeticException("Matrices must be the same size")

        val result = Matrix(rows, cols)

        for (i in 0..<rows) {
            for (j in 0..<cols) {
                result[i, j] += function(get(i, j), other[i, j])
            }
        }

        return result
    }

    operator fun plus(other: Matrix): Matrix {
        return elementwise(other) {a, b -> a + b}
    }

    operator fun minus(other: Matrix): Matrix {
        return elementwise(other) {a, b -> a - b}
    }

    /**
     * Multiply this matrix by [other].
     * Your code should use Strassen's algorithm
     * @return this*[other] if the dimensions are compatible and null otherwise
     */
    fun strassenMultiply(other: Matrix):Matrix? {
        if (rows != other.rows || cols != other.cols) return null
        if (rows != cols) return null
        if (log2(rows.toDouble()) % 1 != 0.0) return null

        val result = Matrix(rows, rows)

        if (rows == 2) {
            val a = get(0, 0)
            val b = get(0, 1)
            val c = get(1, 0)
            val d = get(1, 1)

            val e = other[0, 0]
            val f = other[0, 1]
            val g = other[1, 0]
            val h = other[1, 1]

            val p1 = a * (f - h)
            val p2 = (a + b) * h
            val p3 = (c + d) * e
            val p4 = d * (g - e)
            val p5 = (a + d) * (e + h)
            val p6 = (b - d) * (g + h)
            val p7 = (a - c) * (e + f)

            result[0, 0] = p5 + p4 - p2 + p6
            result[0, 1] = p1 + p2
            result[1, 0] = p3 + p4
            result[1, 1] = p1 + p5 - p3 - p7
        } else {
            val cut = rows/2

            val a = get(0..<cut, 0..<cut)
            val b = get(0..<cut, cut..<rows)
            val c = get(cut..<rows, 0..<cut)
            val d = get(cut..<rows, cut..<rows)

            val e = other[0..<cut, 0..<cut]
            val f = other[0..<cut, cut..<rows]
            val g = other[cut..<rows, 0..<cut]
            val h = other[cut..<rows, cut..<rows]

            val p1 = a.strassenMultiply((f - h))!!
            val p2 = (a + b).strassenMultiply(h)!!
            val p3 = (c + d).strassenMultiply(e)!!
            val p4 = d.strassenMultiply((g - e))!!
            val p5 = (a + d).strassenMultiply((e + h))!!
            val p6 = (b - d).strassenMultiply((g + h))!!
            val p7 = (a - c).strassenMultiply((e + f))!!

            result[0..<cut, 0..<cut] = p5 + p4 - p2 + p6
            result[0..<cut, cut..<rows] = p1 + p2
            result[cut..<rows, 0..<cut] = p3 + p4
            result[cut..<rows, cut..<rows] = p1 + p5 - p3 - p7
        }

        return result
    }
}

fun matrixOf(data: List<List<Double>>): Matrix {
    val rows = data.size
    val cols = max(data.map { it.size })

    val result = Matrix(rows, cols)

    for (i in 0..<rows) {
        for (j in 0..<cols) {
            result[i, j] = data[i][j]
        }
    }

    return result
}