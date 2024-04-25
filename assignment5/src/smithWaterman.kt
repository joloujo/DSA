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

    var loc = H.maxLoc()
    val pairs = mutableListOf<Boolean>()
    val Alist = mutableListOf<T?>()
    val Blist = mutableListOf<T?>()

    while (H[loc.first, loc.second] != 0) {

        val diag = H[loc.first - 1, loc.second - 1]
        val up = H[loc.first - 1, loc.second]
        val left = H[loc.first, loc.second - 1]

        print(listOf(diag, up, left))

        if (diag >= up && diag >= left) {
            print(" diag")
            loc = Pair(loc.first - 1, loc.second - 1)
            pairs.add(true)
            Alist.add(A[loc.first])
            Blist.add(B[loc.second])
        } else {
            pairs.add(false)
            if (up >= left) {
                print(" up")
                loc = Pair(loc.first - 1, loc.second)
                Alist.add(A[loc.first])
                Blist.add(null)
            } else {
                print(" left")
                loc = Pair(loc.first, loc.second - 1)
                Alist.add(null)
                Blist.add(B[loc.second])
            }
        }
        println()
    }

    pairs.reverse()
    Alist.reverse()
    Blist.reverse()

    println(Alist)
    println(pairs)
    println(Blist)
}

fun smithWaterman(A: String, B: String, W: (Int) -> Int, s: (Char, Char) -> Int) {
    val Achars = A.toList()
    val Bchars = B.toList()

    return smithWaterman(Achars, Bchars, W, s)
}