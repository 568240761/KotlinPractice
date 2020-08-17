package coroutine

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.produce

/**
 * 扇出
 *
 * 多个协程也许会接收相同的管道，在它们之间进行分布式工作。
 * 让我们启动一个定期产生整数的生产者协程，接下来我们可以得到几个处理器协程。
 *
 * 还有，注意我们如何使用 for 循环显式迭代通道以在 launchProcessor 代码中执行扇出。
 * 与 consumeEach 不同，这个 for 循环是安全完美地使用多个协程的。
 * 如果其中一个处理器协程执行失败，其它的处理器协程仍然会继续处理通道，
 * 而通过 consumeEach 编写的处理器始终在正常或非正常完成时消耗（取消）底层通道。
 */

fun CoroutineScope.produceNumbers6() = produce<Int> {
    var x = 1 // 从 1 开始
    while (true) {
        send(x++) // 产生下一个数字
        delay(100) // 等待 0.1 秒
    }
}

fun CoroutineScope.launchProcessor(id: Int, channel: ReceiveChannel<Int>) = launch {
    for (msg in channel) {
        println("Processor #$id received $msg")
    }
}

fun main() = runBlocking {
    val producer = produceNumbers6()
    repeat(5) { launchProcessor(it, producer) }
    delay(950)
    producer.cancel() // 取消协程生产者从而将它们全部杀死
}