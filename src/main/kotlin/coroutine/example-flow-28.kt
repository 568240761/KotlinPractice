package coroutine

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking

/**
 * 启动流
 *
 * 使用流表示来自一些源的异步事件是很简单的。
 * 在这个案例中，我们需要一个类似 addEventListener 的函数，
 * 该函数注册一段响应的代码处理即将到来的事件，并继续进行进一步的处理。
 *
 * onEach 操作符可以担任该角色。
 * 然而，onEach 是一个过渡操作符。我们也需要一个末端操作符来收集流。
 * 否则仅调用 onEach 是无效的。
 */

fun simple28(): Flow<Int> = (1..3).asFlow().onEach { delay(100) }

fun main() = runBlocking<Unit> {
    simple28()
        .onEach { event -> println("Event: $event") }
        .collect() // <--- 等待流收集
    println("Done")

    println()

    // launchIn 末端操作符可以在这里派上用场，
    // 使用 launchIn 替换 collect 我们可以在单独的协程中启动流的收集。
    // 注意，launchIn 也会返回一个 Job，
    // 可以在不取消整个作用域的情况下仅取消相应的流收集或对其进行 join。
    simple28()
        .onEach { event -> println("Event: $event") }
        .launchIn(this) // <--- 在单独的协程中执行流
    println("Done")
}