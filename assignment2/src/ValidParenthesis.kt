private val parenthesis = mapOf(
    '(' to ')',
    '{' to '}',
    '[' to ']',
)

/**
 * Determines whether the input [string] has valid parenthesis
 *
 * An input string is valid if:
 * 1. Open brackets must be closed by the same type of brackets.
 * 2. Open brackets must be closed in the correct order.
 * 3. Every close bracket has a corresponding open bracket of the same type.
 *
 * (*From [Leetcode's valid parenthesis problem](https://leetcode.com/problems/valid-parentheses/description/)
 * description*)
 *
 * This implementation also can handle strings with characters other than parenthesis
 *
 * @return `true` if the input [string] has valid parenthesis, `false` otherwise
 */
fun validParenthesis(string: String): Boolean {
    // Make a stack to store the open parenthesis characters
    val stack = StackFromScratch<Char>()

    for (char in string) {
        if (char in parenthesis.keys) {
            // If the character is an open parenthesis, add it to the stack
            stack.push(char)
        } else if (char in parenthesis.values) {
            // If the character is a close parenthesis, check if the most recent unclosed open parenthesis matches
            if (parenthesis.isEmpty() || char != parenthesis[stack.pop()]) {
                return false
            }
        }
    }

    // Make sure all open parenthesis have been closed
    return stack.isEmpty()
}