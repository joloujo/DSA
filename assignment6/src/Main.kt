import kotlinx.coroutines.*
import kotlin.time.TimedValue
import kotlin.time.measureTimedValue

fun main() {
    measureTimedValue {  }

    doSomething()

    print(test(100L) {
        while (true) {
            yield()
        }
    })

    print(measureTimedValue(100L) {
        while (true) {
            yield()
        }
    })
}

fun <T> measureTimedValue(timeout: Long, block: suspend CoroutineScope.() -> T): TimedValue<T>? = runBlocking {
    return@runBlocking withTimeoutOrNull(timeout) {
        measureTimedValue {
            runBlocking {
                block()
            }
        }
    }
}

fun <T> test(timeout: Long, block: suspend CoroutineScope.() -> T): T? = runBlocking {
    return@runBlocking withTimeoutOrNull(timeout) {
        block()
    }
}

fun doSomething() = runBlocking {
    yield()
}