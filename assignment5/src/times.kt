import kotlin.math.pow
import kotlin.random.Random
import kotlin.time.measureTime

fun main() {
    // For powers of 2 from 2 to 1024
    for (power in 1..10) {
        // determine the matrix size
        val size = 2.0.pow(power).toInt()

        // create two random matrices
        val m1 = matrixOf(List(size) {
            List(size) { Random.nextInt(-9, 9) }
        })
        val m2 = matrixOf(List(size) {
            List(size) { Random.nextInt(-9, 9) }
        })

        // Print the matrix size
        println("$size x $size matrix:")

        // Time the traditional multiplication
        val naiveTime = measureTime {
            m1 * m2
        }
        println("  Naive:    $naiveTime")

        // Time the strassen multiplication
        val strassenTime = measureTime{
            m1.strassenMultiply(m2)
        }
        println("  Strassen: $strassenTime")
    }
}