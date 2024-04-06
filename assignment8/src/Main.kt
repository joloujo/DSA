fun main() {
    val rbTree = rbTreeOf(2, 1, 3)
//    println(rbTree)
//    println(if (rbTree.followsInvariant()) "Valid" else "Invalid")
//    println("4 is${if (rbTree.contains(4)) " " else " not "}in the tree")

//    println("-".repeat(10))

    rbTree.add(4)
    println(rbTree)
//    println(if (rbTree.followsInvariant()) "Valid" else "Invalid")
//    println("4 is${if (rbTree.contains(4)) " " else " not "}in the tree")

//    println("-".repeat(10))

    rbTree.rotate(rbTree.root!![RIGHT]!!)
    println(rbTree)

    println("-".repeat(10))

    rbTree.rotate(rbTree.root!![LEFT]!!)
    println(rbTree)
}