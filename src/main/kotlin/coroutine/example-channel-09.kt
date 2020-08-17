package coroutine

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel

/**
 * 通道是公平的
 *
 * 发送和接收操作是公平的并且尊重调用它们的多个协程。
 * 它们遵守先进先出原则，可以看到第一个协程调用 receive 并得到了元素。
 * 在下面的例子中两个协程“乒”和“乓”都从共享的“桌子”通道接收到这个“球”元素。
 */
fun main() = runBlocking {
    val table = Channel<Ball>() // 一个共享的 table（桌子）
    launch { player("ping", table) }
    launch { player("pong", table) }
    table.send(Ball(0)) // 乒乓球
    delay(1000) // 延迟 1 秒钟
    coroutineContext.cancelChildren() // 游戏结束，取消它们
}

suspend fun player(name: String, table: Channel<Ball>) {
    for (ball in table) { // 在循环中接收球
        ball.hits++
        println("$name $ball")
        delay(300) // 等待一段时间
        table.send(ball) // 将球发送回去
    }
}

data class Ball(var hits: Int)