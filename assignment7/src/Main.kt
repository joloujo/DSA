import java.io.File

fun main() {
    // Benchmark The Raven and Romeo and Juliet
    benchmarkString("The Raven", "data/theRaven.txt")
    benchmarkString("Romeo and Juliet", "data/romeoAndJuliet.txt")
}

fun benchmarkString(name: String, filepath: String) {
    // Print the title
    println(name)

    // Parse the text from the file
    var text = ""
    File(filepath).readLines().forEach{ text += it + '\n' }

    // Print the uncompressed size
    val uncompressedSize = text.length * 8
    println("    Uncompressed size: $uncompressedSize bits")

    println("    LZ compression with strings/chars")

    // Find and print the compressed size using LZ compression with strings/chars
    val compressed = lempelZiv(text)
    val compressedSize = compressed.size
    println("        Compressed size: $compressedSize bits")

    // Print the compression ratios for easy comparison
    println("        Compression ratio: ${uncompressedSize.toDouble()/compressedSize.toDouble()}")
    println("        Space Saving: ${1 - compressedSize.toDouble()/uncompressedSize.toDouble()}")

    println("    LZ compression with bits:")

    // Convert the text into a bit array
    val bits = mutableListOf<Boolean>()
    for (c in text) {
        bits.addAll(c.toBits())
    }

    // Find and print the compressed size by first converting to a bit array and then using LZ compression with bits
    val compressedBits = lempelZiv(bits)
    val compressedBitsSize = compressedBits.size
    println("        Compressed size: $compressedBitsSize bits:")

    // Print the compression ratios for easy comparison
    println("        Compression ratio: ${uncompressedSize.toDouble()/compressedBitsSize.toDouble()}")
    println("        Space Saving: ${1 - compressedBitsSize.toDouble()/uncompressedSize.toDouble()}")
}