package coroutine

import kotlinx.coroutines.*
import kotlin.system.*

/**
 * 使用 async 的结构化并发#1：
 *
 * 让我们使用使用 async 的并发这一小节的例子，
 * 提取出一个函数并发的调用 [doSomethingUsefulOne] 与 [doSomethingUsefulTwo] 并且返回它们两个的结果之和。
 * 由于 async被定义为了 CoroutineScope 上的扩展，
 * 我们需要将它写在作用域内，并且这是 coroutineScope 函数所提供的：
 */
fun main() = runBlocking<Unit> {
    val time = measureTimeMillis {
        println("The answer is ${concurrentSum()}")
    }
    println("Completed in $time ms")
}

/**
 * 这种情况下，如果在 [concurrentSum] 函数内部发生了错误，
 * 并且它抛出了一个异常， 所有在作用域中启动的协程都会被取消。
 */
suspend fun concurrentSum(): Int = coroutineScope {
    val one = async { doSomethingUsefulOne() }
    val two = async { doSomethingUsefulTwo() }
    one.await() + two.await()
}