package coroutine

import kotlinx.coroutines.*

/**
 * 全局协程像守护线程:
 *
 * 以下代码在 GlobalScope 中启动了一个长期运行的协程，
 * 该协程每秒输出“I'm sleeping”两次，之后在主函数中延迟一段时间后返回。
 */
fun main() = runBlocking {
    val job = GlobalScope.launch {
        repeat(1000) { i ->
            println("I'm sleeping $i ...")
            delay(500L)
        }
    }
    //手动取消协程，但是无法结构化的并发取消
    job.cancel()
    delay(1300L) // just quit after delay
}