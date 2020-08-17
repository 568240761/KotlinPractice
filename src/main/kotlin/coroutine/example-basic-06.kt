package coroutine

import kotlinx.coroutines.*

/**
 * 协程和线程池很像
 *
 * 它启动了 10 万个协程，并且在一秒钟后，每个协程都输出一个点。
 */
fun main() = runBlocking {
    println("main=${Thread.currentThread().name}")
    repeat(100_000) {
        // launch a lot of coroutines
        println("main1=${Thread.currentThread().name}")
        launch {
            delay(1000L)
            println("main2=${Thread.currentThread().name}")
            println(".")
        }
    }
}