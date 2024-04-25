import kotlin.math.pow
import kotlin.random.Random
import kotlin.time.measureTime

fun main() {
    for (power in 1..10) {
        val size = 2.0.pow(power).toInt()

        val m1 = matrixOf(List(size) {
            List(size) { Random.nextInt(-9, 9) }
        })
        val m2 = matrixOf(List(size) {
            List(size) { Random.nextInt(-9, 9) }
        })

        println("$size x $size matrix:")

        val naiveTime = measureTime {
            m1 * m2
        }
        println("  Naive:    $naiveTime")

        val strassenTime = measureTime{
            m1.strassenMultiply(m2)
        }
        println("  Strassen: $strassenTime")
    }
}