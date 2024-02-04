import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class ValidParenthesisTest {

    @Test
    fun testValidParenthesis() {
        val cases = mapOf(
            "" to true,
            "()" to true,
            "{}" to true,
            "[]" to true,
            "{()}" to true,
            "{}()" to true,
            "{[{}]()}" to true,
            "{([]{})[]({})}" to true,
            "(" to false,
            ")" to false,
            "{}[" to false,
            "({)" to false,
            "({)}" to false,
            "][" to false,
            "({[])}" to false,
            "{This [also] w()rks}" to true,
            "Even if there's ()ther (haracters" to false,
            "{inclu[)ed}" to false,
        )

        for (key in cases.keys) {
            assertEquals(cases[key], validParenthesis(key), "Failed on '$key'")
        }
    }
}