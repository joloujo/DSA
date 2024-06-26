import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class lempelZivKtTest {

    /**
     * Test the [Int.toBits] function
     */
    @Test
    fun toBits() {
        // Make sure it works
        assertEquals(listOf(true, false, true, false, true, false), 42.toBits())

        // Make sure it works for 0
        assertEquals(listOf(false), 0.toBits())

        // Make sure it pads if needed
        assertEquals(listOf(false, false, true, true), 3.toBits(4))

        // Make sure it doesn't like negative numbers
        org.junit.jupiter.api.assertThrows<ArithmeticException> { (-1).toBits() }

        // Make sure it doesn't like a negative number of bits
        org.junit.jupiter.api.assertThrows<ArithmeticException> { (3).toBits(-1) }

        // Make sure it knows when it cant fit a number in the specified amount of bits
        org.junit.jupiter.api.assertThrows<ArithmeticException> { (10).toBits(3) }
    }

    /**
     * Test the lempelZiv function with strings. This example is from Peter Shor's notes
     */
    @Test
    fun lempelZivBits() {
        val input = "AABABBBABAABABBBABBABB".toCharArray().map { it == 'B' }
        val output = "001110100101001011100101100111".toCharArray().map { it == '1' }
        assertEquals(output, lempelZiv(input))
    }

    /**
     * Test the lempelZiv function with a bit array. This example is from the
     * [Lempel–Ziv–Welch wikipedia page](https://en.wikipedia.org/wiki/Lempel%E2%80%93Ziv%E2%80%93Welch).
     * The end behavior on this example lines up with adding a pair to the codebook
     */
    @Test
    fun lempelZivString1() {
        val input = "TOBEORNOTTOBEORTOBEORNOT#"
        /* The first steps to finding the solution, done by hand
        1  2  3  4  5  6  7  8  9  10  11 12 13 14 15
        T  O  B  E  OR N  OT TO BE ORT OB EO R  NO T#
        0T 0O 0B 0E 2R 0N 2T 1O 3E 5T  2B 4O 0R 6O 1#
        */

        // A string representation of the output in the format code,char|...
        val outputString = "0,T|0,O|00,B|00,E|010,R|000,N|010,T|001,O|0011,E|0101,T|0010,B|0100,O|0000,R|0110,O|0001,#"

        // Find the output bit array from the output string
        val output = mutableListOf<Boolean>()
        for (c in outputString) {
            when (c) {
                '0' -> output.add(false)
                '1' -> output.add(true)
                ',', '|' -> continue
                else -> {
                    output.addAll(c.code.toBits(8))
                }
            }
        }

        // Test lempelZiv
        assertEquals(output, lempelZiv(input))

    }

    /**
     * Test the lempelZiv function with a bit array. This example is modified from the
     * [Lempel–Ziv–Welch wikipedia page](https://en.wikipedia.org/wiki/Lempel%E2%80%93Ziv%E2%80%93Welch).
     * This example was modified so the end behavior on does not line up with adding a pair to the codebook. This tests
     * more code.
     */
    @Test
    fun lempelZivString2() {
        val input = "TOBEORNOTTOBEORTOBEORNOT"
        /* The first steps to finding the solution, done by hand
        1  2  3  4  5  6  7  8  9  10  11 12 13 14 15
        T  O  B  E  OR N  OT TO BE ORT OB EO R  NO T
        0T 0O 0B 0E 2R 0N 2T 1O 3E 5T  2B 4O 0R 6O 1
        */

        // A string representation of the output in the format code,char|...
        val outputString = "0,T|0,O|00,B|00,E|010,R|000,N|010,T|001,O|0011,E|0101,T|0010,B|0100,O|0000,R|0110,O|0001"

        // Find the output bit array from the output string
        val output = mutableListOf<Boolean>()
        for (c in outputString) {
            when (c) {
                '0' -> output.add(false)
                '1' -> output.add(true)
                ',', '|' -> continue
                else -> {
                    output.addAll(c.code.toBits(8))
                }
            }
        }

        // Test lempelZiv
        assertEquals(output, lempelZiv(input))

    }
}