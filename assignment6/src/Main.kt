fun main() {
    val g = MutableGraph<Char>()

    g.addAll('A', 'B', 'C', 'D')
    g['A', 'B'] = 1
    g['B', 'A'] = 3
    println(g)

    g.add('E')
    g.removeAll('B', 'C', 'F')
    println(g)

    g.add('E')
    println(g)
}