package coroutine

import kotlinx.coroutines.*

/**
 * 等待一个作业：
 *
 * 延迟一段时间来等待另一个协程运行并不是一个好的选择。
 * 让我们显式（以非阻塞方式）等待所启动的后台 Job 执行结束
 */
fun main() = runBlocking {
    val job = GlobalScope.launch { // launch a new coroutine and keep a reference to its Job
        delay(1000L)
        println("World!")
    }
    println("Hello,")
    job.join() // wait until child coroutine completes
}