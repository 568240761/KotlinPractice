package coroutine

import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

/**
 * 共享的可变状态与并发
 *
 * 协程可用多线程调度器（比如默认的 Dispatchers.Default）并发执行。
 * 这样就可以提出所有常见的并发问题。
 * 主要的问题是同步访问共享的可变状态。
 * 协程领域对这个问题的一些解决方案类似于多线程领域中的解决方案， 但其它解决方案则是独一无二的。
 */
suspend fun massiveRun(action: suspend () -> Unit) {
    val n = 100  // number of coroutines to launch
    val k = 1000 // times an action is repeated by each coroutine
    val time = measureTimeMillis {
        coroutineScope { // scope for coroutines
            repeat(n) {
                launch {
                    repeat(k) { action() }
                }
            }
        }
    }
    println("Completed ${n * k} actions in $time ms")
}

var counter = 0

fun main() = runBlocking {
    withContext(Dispatchers.Default) {
        massiveRun {
            counter++
        }
    }

    //它不太可能打印出“Counter = 100000”，
    //因为一百个协程在多个线程中同时递增计数器但没有做并发处理。
    println("Counter = $counter")
}