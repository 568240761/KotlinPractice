package coroutine

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce

/**
 * 构建通道生产者
 *
 * 协程生成一系列元素的模式很常见。
 * 这是 生产者——消费者 模式的一部分，并且经常能在并发的代码中看到它。
 * 你可以将生产者抽象成一个函数，并且使通道作为它的参数，
 * 但这与必须从函数中返回结果的常识相违悖。
 *
 * 使用 produce 的便捷的协程构建器，可以很容易的在生产者端正确工作，
 * 并且我们使用扩展函数 consumeEach 在消费者端替代 for 循环：
 */
fun CoroutineScope.produceSquares(): ReceiveChannel<Int> = produce {
    for (x in 1..5) send(x * x)
}

fun main() = runBlocking {
    val squares = produceSquares()
    squares.consumeEach { println(it) }
    println("Done!")
}