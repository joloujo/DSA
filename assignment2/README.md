# Assignment 2

This is the project for [Assignment 2 of DSA](https://olindsa2024.github.io/assignments/assignment_02).

## Theoretical Implementations

**Stack Reversing**

Because a stack is LIFO, it is very easy to reverse it. Using one additional stack, pop items off the original stack and
push them to the new stack until there are no items in the old stack. The last item out of the original stack will be
the last item into the new stack, and therefore the first one out of the new stack. You can then use the new stack as
your reversed stack.

If you need the reference to the stack to stay the same (if it's a `val` and not a `var` for instance), then you can use
a queue. Pop all the items from the stack and enqueue them into the queue. Because the queue is FIFO, it will have the 
same order as the original stack. Then, dequeue all the items back into the original stack. By the same logic as the two
stacks solution, since the stack is LIFO, this will reverse the items in the stack.

**Valid Parentheses Problem**

This problem can be solved using a stack. Create a map from opening parenthesis to the matching closing parenthesis.
When you encounter a character that is in the map keys, push it to the stack. When you encounter a character that is in
the map values, pop the top char in the stack and make sure they correspond using the map. If the characters ever don't
match, the string contains invalid parenthesis. If they all match, and the stack is empty at the end (no unclosed 
parenthesis), the string contains valid parenthesis.

This is implemented as `validParenthesis()` in the [`ValidParenthesis.kt`](src/ValidParenthesis.kt) file.

**Copy Stack Problem**

Every time you remove the elements from a stack, it reverses their order. To know what is at the bottom of the stack,
you need to remove all the elements, meaning you'd have to reverse it at least once. Because the order needs to be the 
same as the original stack, you have to reverse it a second time.

To do all of these reversals, you first pop all the values from the original stack and push them into the stack you are
copying to. This reverses the order. Then you pop all the values from the new stack and enqueue them into an auxiliary
queue. This reverses the order again, returning it to the original order, and empties the new stack, leaving both stacks
empty. Now, it's as simple as dequeueing all the values from the queue and adding them to the original and new stack.