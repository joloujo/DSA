import java.util.Collections.max

fun <T> smithWaterman(A: List<T>, B: List<T>, W: (Int) -> Int, s: (T, T) -> Int) {
    val H = IntMatrix(A.size + 1, B.size + 1)
    val traceback = Matrix<Pair<Int, Int>>(A.size + 1, B.size + 1)

    for (i in 1..A.size) {
        for (j in 1..B.size) {
            val matchScore = H[i-1, j-1] + s(A[i-1], B[j-1])
            val gap1Score = max((1..i).map { k -> H[i-k, j] - W(k) })
            val gap2Score = max((1..j).map { l -> H[i, j-l] - W(l) })

            val (value, path) = max(
                listOf(
                    matchScore, gap1Score, gap2Score, 0).zip(listOf(
                    Pair(i-1, j-1), Pair(i-1, j), Pair(i, j-1), null)),
                compareBy {
                    it.first
                }
            )

            H[i, j] = value
            traceback[i, j] = path
        }
    }

    println(H)
    println(traceback)

    var loc = H.maxLoc()
    val pairs = mutableListOf<Boolean>()
    val Alist = mutableListOf<T?>()
    val Blist = mutableListOf<T?>()

    while (traceback[loc.first, loc.second] != null) {
        val next = traceback[loc.first, loc.second]!!

        // Diagonal case
        when (loc - next) {
            // diagonal case
            Pair(1, 1) -> {
                pairs.add(true)
                Alist.add(A[loc.first-1])
                Blist.add(B[loc.second-1])
            }
            // up case
            Pair(1, 0) -> {
                pairs.add(false)
                Alist.add(A[loc.first-1])
                Blist.add(null)
            }
            // left case
            Pair(0, 1) -> {
                pairs.add(false)
                Alist.add(null)
                Blist.add(B[loc.second-1])
            }
        }

        loc = next
    }

    pairs.reverse()
    Alist.reverse()
    Blist.reverse()

    println(Blist.map { it ?: '-' }.joinToString(separator = ""))
    println(pairs.map {if (it) '|' else ' '}.joinToString(separator = ""))
    println(Alist.map { it ?: '-' }.joinToString(separator = ""))
}

fun smithWaterman(A: String, B: String, W: (Int) -> Int, s: (Char, Char) -> Int) {
    val Achars = A.toList()
    val Bchars = B.toList()

    return smithWaterman(Achars, Bchars, W, s)
}

operator fun Pair<Int, Int>.minus(other: Pair<Int, Int>): Pair<Int, Int> {
    return Pair(this.first - other.first, this.second - other.second)
}