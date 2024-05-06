//for (i in 1..cycles) {
//    val g = MutableGraph<Int>()
//    g.addAll(1..size)
//
//    g.randomize(nullProbability = 0.5)
//
//    val (bestPath1, minWeight1) = tspBruteForce(g)
//
//    val (bestPath2, minWeight2) = tspReverseTree(g)
//
//    assertEquals(minWeight1, minWeight2,
//        "--- ERROR ---\n" +
//                "$g\n" +
//                "BRUTE FORCE 1: $bestPath1 | $minWeight1\n" +
//                "BRUTE FORCE 2: $bestPath2 | $minWeight2"
//    )
//
//    if (bestPath1 != bestPath2) {
//        println("$bestPath1 | $bestPath2")
//    }
//}
//
//val g = MutableGraph<Int>()
//g.addAll(1..10)
//
//g.randomize(nullProbability = 0.5)
//println(g)
//
//val (bestPath, minWeight) = tspNearestNeighbor(g)
//println("GREEDY: $bestPath | $minWeight")