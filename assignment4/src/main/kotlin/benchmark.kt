import kotlin.math.pow
import kotlin.math.sqrt
import kotlin.random.Random
import kotlin.time.DurationUnit
import kotlin.time.measureTime
import java.io.File


/**
 * Finds mean and standard deviation of the runtime of a given [sort]ing algorithm for a list of a given [length]. Runs
 * the algorithm a given number of [cycles]
 * @return a [Pair] of the mean and standard deviation of the runtime
 */
fun benchmark(length: Int, sort: (list: MutableList<Int>) -> Unit, cycles: Int = 100): Pair<Double, Double> {
    val times = mutableListOf<Double>()

    for (i in 1..cycles) {
        val time = measureTime {
            val randomList = MutableList(length) { Random.nextInt(0, 1000) }
            sort(randomList)
        }
        times.add(time.toDouble(DurationUnit.SECONDS))
    }

    val mean = times.average()
    val variance = times.map { (it - mean).pow(2) }.average()
    val stddev = sqrt(variance)
    return Pair(mean, stddev)
}

fun main() {
    val file = File("benchmark.csv")



    file.writeText("1,2\n3,4")
}