import org.jetbrains.kotlinx.kandy.dsl.*
import org.jetbrains.kotlinx.kandy.ir.scale.Scale
import org.jetbrains.kotlinx.kandy.letsplot.export.save
import org.jetbrains.kotlinx.kandy.letsplot.layers.*
import org.jetbrains.kotlinx.kandy.letsplot.scales.Transformation
import org.jetbrains.kotlinx.kandy.letsplot.x
import kotlin.math.pow
import kotlin.math.sqrt
import kotlin.random.Random
import kotlin.time.DurationUnit
import kotlin.time.measureTime
import java.io.File
import java.util.Collections.min
import java.util.Collections.max

/** The number of cycles to benchmark for */
const val CYCLES = 1000
/** The max value to test with */
const val LIST_MAX = 9999
/** The power of 2 of the length of the list */
const val LENGTH_POWER = 13

/**
 * Finds mean and standard deviation of the runtime of a given [sort]ing algorithm for a list of a given [length]. Runs
 * the algorithm a given number of [cycles]
 * @return a [Pair] of the mean and standard deviation of the runtime
 */
fun benchmark(sort: (list: MutableList<Int>) -> Unit, length: Int, cycles: Int = CYCLES): Pair<Double, Double> {
    // Create a list to store the times
    val times = mutableListOf<Double>()

    for (i in 1..cycles) {
        // Measure the time it takes to sort
        val time = measureTime {
            // Sort a random list
            val randomList = MutableList(length) { Random.nextInt(LIST_MAX) }
            sort(randomList)
        }
        // Add the time to the list of times
        times.add(time.toDouble(DurationUnit.SECONDS))
    }

    // Find the average time
    val mean = times.average()

    // Find the standard deviation of the time
    val variance = times.map { (it - mean).pow(2) }.average()
    val stddev = sqrt(variance)

    return Pair(mean, stddev)
}

fun main() {
    // Set up what sorts to benchmark and what lengths to benchmark with
    val sorts = mapOf<String, (list: MutableList<Int>) -> Unit>(
        "Merge" to ::mergeSort,
        "Radix" to ::radixSort,
        "Selection" to ::selectionSort,
        "Quick" to ::quickSort,
    )
    val lengths = List(LENGTH_POWER) { 2.0.pow(it.toDouble() + 1).toInt() }

    // Set up lists to store data
    val sortCol = mutableListOf<String>()
    val lengthCol = mutableListOf<Int>()
    val timeCol = mutableListOf<Pair<Double, Double>>()

    // Set up the file to write data to
    val file = File("benchmarking/benchmark.csv")
    // Set up a string to eventually write to the file, and add headers
    var text = "sort, length, mean, stddev\n"

    // Run benchmark for each sort/length combination
    for (length in lengths) {
        // Inform the user where in the process the program is
        println("Length: $length")
        for (sort in sorts) {
            // Inform the user where in the process the program is
            println("  Sort: ${sort.key}")

            // Store the sort used, length tested, and resulting time
            sortCol.add(sort.key)
            lengthCol.add(length)

            val time = benchmark(sort.value, length)
            timeCol.add(time)

            // Add the data to the text to be written to the file
            text += "${sort.key}, $length, ${time.first}, ${time.second}\n"
        }
    }

    // Write the data to the file
    file.writeText(text)

    val meanTimes = timeCol.map { it.first }

    // Plot the data
    plot {
        // Plot list length along x in log scale
        x(lengthCol) {
            scale = Scale.continuousPos(min(lengthCol), max(lengthCol), transform = Transformation.LOG10)
            axis.name = "List length (items)"
        }

        // Plot average time along y in log scale with both points and lines
        points {
            y(meanTimes) {
                scale = Scale.continuousPos(min(meanTimes), max(meanTimes), transform = Transformation.LOG10)
                axis.name = "Average time (seconds)"
            }
            color(sortCol)
        }
        line {
            y(meanTimes) {scale = Scale.continuousPos(min(meanTimes), max(meanTimes), transform = Transformation.LOG10)}
            color(sortCol)
        }
    }.save("benchmark.png", path="benchmarking/")
}