fun main() {
    smithWaterman("GGTTGACTA", "TGTTACGG", {a -> 2 * a}, {a, b -> if (a == b) 3 else -3})
}