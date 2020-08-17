package coroutine

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.produce

/**
 * 使用管道的素数
 *
 * 在协程中使用一个管道来生成素数。我们开启了一个数字的无限序列。
 *
 * 注意，你可以在标准库中使用 iterator 协程构建器来构建一个相似的管道。
 * 使用 iterator 替换 produce、yield 替换 send、next 替换 receive、
 * Iterator 替换 ReceiveChannel 来摆脱协程作用域，你将不再需要 runBlocking。
 * 然而，如上所示，如果你在 Dispatchers.Default 上下文中运行它，使用通道的管道的好处在于它可以充分利用多核心 CPU。
 * 不过，这是一种非常不切实际的寻找素数的方法。
 * 在实践中，管道调用了另外的一些挂起中的调用（就像异步调用远程服务）
 * 并且这些管道不能内置使用 sequence/iterator，因为它们不被允许随意的挂起，不像 produce 是完全异步的。
 */
fun main() = runBlocking {
    var cur = numbersFrom(2)
    repeat(10) {
        val prime = cur.receive()
        println(prime)
        cur = filter(cur, prime)
    }
    coroutineContext.cancelChildren() // 取消所有的子协程来让主协程结束
}

fun CoroutineScope.numbersFrom(start: Int) = produce {
    var x = start
    while (true) {
        x++
        println("send=$x")
        send(x)
    } // 开启了一个无限的整数流
}

//删除了所有可以被给定素数整除的数字
fun CoroutineScope.filter(numbers: ReceiveChannel<Int>, prime: Int) = produce<Int> {
    for (x in numbers) if (x % prime != 0) send(x)
}