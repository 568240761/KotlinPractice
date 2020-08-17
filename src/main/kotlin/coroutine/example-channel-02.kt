package coroutine

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel

/**
 * 关闭与迭代通道
 *
 * 和队列不同，一个通道可以通过被关闭来表明没有更多的元素将会进入通道。
 * 在接收者中可以定期的使用 for 循环来从通道中接收元素。
 *
 * 从概念上来说，一个 close 操作就像向通道发送了一个特殊的关闭指令。
 * 这个迭代停止就说明关闭指令已经被接收了。所以这里保证所有先前发送出去的元素都在通道关闭前被接收到。
 */
fun main() = runBlocking {
    val channel = Channel<Int>()
    launch {
        for (x in 1..5) channel.send(x * x)
        channel.close() // 我们结束发送
    }
    // 这里我们使用 `for` 循环来打印所有被接收到的元素（直到通道被关闭）
    for (y in channel) println(y)
    println("Done!")
}