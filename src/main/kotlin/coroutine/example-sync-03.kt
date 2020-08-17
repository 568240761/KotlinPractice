package coroutine

import kotlinx.coroutines.*
import java.util.concurrent.atomic.AtomicInteger
import kotlin.system.measureTimeMillis

/**
 * 以细粒度限制线程
 *
 * 限制线程是解决共享可变状态问题的一种方案：对特定共享状态的所有访问权都限制在单个线程中。
 * 它通常应用于 UI 程序中：所有 UI 状态都局限于单个事件分发线程或应用主线程中。
 * 这在协程中很容易实现，通过使用一个单线程上下文。
 */

suspend fun massiveRun3(action: suspend () -> Unit) {
    val n = 100  // number of coroutines to launch
    val k = 1000 // times an action is repeated by each coroutine
    val time = measureTimeMillis {
        coroutineScope {
            // scope for coroutines
            repeat(n) {
                launch {
                    repeat(k) { action() }
                }
            }
        }
    }
    println("Completed ${n * k} actions in $time ms")
}

val counterContext = newSingleThreadContext("CounterContext")
var counter3 = 0

fun main() = runBlocking {
    withContext(Dispatchers.Default) {
        massiveRun3 {
            withContext(counterContext) {
                counter3++
            }
        }
    }

    //这段代码运行非常缓慢，因为它进行了细粒度的线程限制。
    //每个增量操作都得从从多线程[Dispatchers.Default]上下文切换到单线程上下文。
    println("Counter = $counter3")
}