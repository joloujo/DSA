import java.util.*
import kotlin.math.log2

class IntMatrix(rows: Int, cols: Int) : Matrix<Int?>(rows, cols) {

    init {
        data.replaceAll { MutableList(cols) {0} }
    }

    override operator fun get(row: Int, col: Int): Int {
        return data[row][col]!!
    }

    override operator fun get(rows: IntRange, cols: IntRange): IntMatrix {
        val newData = rows.map {i -> cols.map {j -> data[i][j]!!}}
        return matrixOf(newData)
    }

    /**
     * Multiply this matrix by [other].
     * You can implement this either using block-based matrix multiplication or
     * traditional matrix multiplication (the kind you learn about in math
     * classes!)
     * @return this*[other] if the dimensions are compatible and all entries are filled and null otherwise
     *
     */
    private fun multiply(other: IntMatrix):IntMatrix? {
        if (cols != other.rows) return null

        val n = cols

        val result = IntMatrix(rows, other.cols)

        for (i in 0..<rows) {
            for (j in 0..<other.cols) {
                for (k in 0..<n) {
                    result[i, j] += get(i, k) * other[k, j]
                }
            }
        }

        return result
    }

    operator fun times(other: IntMatrix): IntMatrix? {
        return multiply(other)
    }

    private fun elementwise(other: IntMatrix, function: (Int, Int) -> Int): IntMatrix {
        if (rows != other.rows || cols != other.cols) throw ArithmeticException("Matrices must be the same size")

        val result = IntMatrix(rows, cols)

        for (i in 0..<rows) {
            for (j in 0..<cols) {
                val a = get(i, j)
                val b = other[i, j]
                val value = function(a, b)
                result[i, j] += value
            }
        }

        return result
    }

    operator fun plus(other: IntMatrix): IntMatrix {
        return elementwise(other) {a, b -> a + b}
    }

    operator fun minus(other: IntMatrix): IntMatrix {
        return elementwise(other) {a, b -> a - b}
    }

    /**
     * Multiply this matrix by [other].
     * Your code should use Strassen's algorithm
     * @return this*[other] if the dimensions are compatible and null otherwise
     */
    fun strassenMultiply(other: IntMatrix): IntMatrix? {
        if (rows != other.rows || cols != other.cols) return null
        if (rows != cols) return null
        if (log2(rows.toDouble()) % 1 != 0.0) return null

        val result = IntMatrix(rows, rows)

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

    fun maxLoc(): Pair<Int, Int> {
        var max = Int.MIN_VALUE
        var loc = Pair(0, 0)

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

fun matrixOf(data: List<List<Int>>): IntMatrix {
    val rows = data.size
    val cols = Collections.max(data.map { it.size })

    val result = IntMatrix(rows, cols)

    for (i in 0..<rows) {
        for (j in 0..<cols) {
            result[i, j] = data[i][j]
        }
    }

    return result
}