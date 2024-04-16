import org.jetbrains.kotlinx.dataframe.api.dataFrameOf
import org.jetbrains.kotlinx.kandy.dsl.plot
import org.jetbrains.kotlinx.kandy.letsplot.export.save
import org.jetbrains.kotlinx.kandy.letsplot.feature.layout
import org.jetbrains.kotlinx.kandy.letsplot.layers.bars

fun main() {
    // Create a DataFrame with data on average temperatures in various cities
    val averageTemperature = dataFrameOf(
        "city" to listOf("New York", "London", "Berlin", "Yerevan", "Tokyo"),
        "average temperature" to listOf(12.5, 11.0, 9.6, 11.5, 16.0)
    )

    // Construct a plot using the data from the DataFrame
    averageTemperature.plot {
        // Add bars to the plot
        // Each bar represents the average temperature in a city
        bars {
            x("city") // Set the cities' data on the X-axis
            y("average temperature") { // Set the temperatures' data on the Y-axis
                axis.name = "Average Temperature (°C)" // Assign a name to the Y-axis
            }
        }
        // Set the title of the plot
        layout.title = "Kandy Getting Started Example"
    }.save("Started Example.png", path="img/") // Save plot as PNG image
}