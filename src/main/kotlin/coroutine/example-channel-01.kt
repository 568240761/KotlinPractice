package coroutine

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel

/**
 * 通道
 *
 * 延期的值提供了一种便捷的方法使单个值在多个协程之间进行相互传输。
 * 通道提供了一种在流中传输值的方法。
 *
 * 通道基础
 *
 * 一个 Channel 是一个和 BlockingQueue 非常相似的概念。
 * 其中一个不同是它代替了阻塞的 put 操作并提供了挂起的 send，
 * 还替代了阻塞的 take 操作并提供了挂起的 receive。
 */

fun main() = runBlocking {
    val channel = Channel<Int>()
    launch {
        // 这里可能是消耗大量 CPU 运算的异步逻辑，我们将仅仅做 5 次整数的平方并发送
        for (x in 1..5) channel.send(x * x)
    }
    // 这里我们打印了 5 次被接收的整数：
    repeat(5) { println(channel.receive()) }
    println("Done!")
}