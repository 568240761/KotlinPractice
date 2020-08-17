package coroutine

import kotlinx.coroutines.*
import java.util.concurrent.atomic.AtomicInteger
import kotlin.system.measureTimeMillis

/**
 * 以粗粒度限制线程
 *
 * 在实践中，线程限制是在大段代码中执行的，例如：状态更新类业务逻辑中大部分都是限于单线程中。
 * 下面的示例演示了这种情况， 在单线程上下文中运行每个协程。
 */

suspend fun massiveRun4(action: suspend () -> Unit) {
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

val counterContext4 = newSingleThreadContext("CounterContext")
var counter4 = 0

fun main() = runBlocking {
    withContext(counterContext4) {
        massiveRun4 {
            counter4++
        }
    }

    println("Counter = $counter4")
}