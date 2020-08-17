package coroutine

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel

/**
 * 带缓冲的通道
 *
 * 到目前为止展示的通道都是没有缓冲区的。
 * 无缓冲的通道在发送者和接收者相遇时传输元素（也称“对接”）。
 * 如果发送先被调用，则它将被挂起直到接收被调用；
 * 如果接收先被调用，它将被挂起直到发送被调用。
 *
 * Channel() 工厂函数与 produce 建造器通过一个可选的参数 capacity 来指定缓冲区大小。
 * 缓冲允许发送者在被挂起前发送多个元素，
 * 就像 BlockingQueue 有指定的容量一样，当缓冲区被占满的时候将会引起阻塞。
 */
fun main() = runBlocking {
    val channel = Channel<Int>(4) // 启动带缓冲的通道
    val sender = launch {
        // 启动发送者协程
        repeat(10) {
            println("Sending $it") // 在每一个元素发送前打印它们
            channel.send(it) // 将在缓冲区被占满时挂起
        }
    }
    // 没有接收到东西……只是等待……
    delay(1000)
    sender.cancel() // 取消发送者协程
}