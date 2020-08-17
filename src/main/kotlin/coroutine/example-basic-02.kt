package coroutine

import kotlinx.coroutines.*

/**
 * 桥接阻塞与非阻塞的世界:
 *
 * example-basic-01.kt在同一段代码中混用了非阻塞的 delay(……) 与阻塞的 Thread.sleep(……) 。
 * 这容易让我们记混哪个是阻塞的、哪个是非阻塞的。
 * 让我们显式使用 runBlocking 协程构建器来阻塞。
 *
 * example-basic-01.kt与example-basic-02.kt结果是相似的，
 * 但是这些代码只使用了非阻塞的函数 delay。
 * 调用了 runBlocking 的主线程会一直阻塞直到 runBlocking 内部的协程执行完毕。
 */
fun main() {
    GlobalScope.launch {
        // launch a new coroutine in background and continue
        delay(1000L)
        println("World!")
    }
    println("Hello,") // main thread continues here immediately
    runBlocking {
        // but this expression blocks the main thread
        delay(2000L)  // ... while we delay for 2 seconds to keep JVM alive
    }
}