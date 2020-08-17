package coroutine

import kotlinx.coroutines.*

/**
 * 使用 async 的结构化并发#2:
 *
 * 取消始终通过协程的层次结构来进行传递。
 */
fun main() = runBlocking<Unit> {
    try {
        failedConcurrentSum()
    } catch(e: ArithmeticException) {
        println("Computation failed with ArithmeticException")
    }
}

/**
 * 请注意，如果其中一个子协程（即 two ）失败，第一个 async 以及等待中的父协程都会被取消。
 */
suspend fun failedConcurrentSum(): Int = coroutineScope {
    val one = async<Int> {
        try {
            delay(Long.MAX_VALUE) // Emulates very long computation
            42
        } finally {
            println("First child was cancelled")
        }
    }
    val two = async<Int> {
        println("Second child throws an exception")
        throw ArithmeticException()
    }
    one.await() + two.await()
}