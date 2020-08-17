package coroutine

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.concurrent.thread

/**
 * 协程是点像线程池。
 * 它们在某些 [CoroutineScope] 上下文中与 [launch] 协程构建器一起启动。
 * 这里我们在 [GlobalScope] 中启动了一个新的协程，
 * 这意味着新协程的生命周期只受整个应用程序的生命周期限制。
 */
fun main() {
    println("main=${Thread.currentThread().name}")
    thread {
        // launch a new coroutine in background and continue
        println("launch=${Thread.currentThread().name}")
        Thread.sleep(1000L) // non-blocking delay for 1 second (default time unit is ms)
        println("delay=${Thread.currentThread().name}")

        println("World!") // print after delay
        println("delay1=${Thread.currentThread().name}")
    }

    println("Hello,") // main thread continues while coroutine is delayed
    println("main1=${Thread.currentThread().name}")
    Thread.sleep(2000L) // block main thread for 2 seconds to keep JVM alive
}