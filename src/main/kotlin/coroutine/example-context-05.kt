package coroutine

import kotlinx.coroutines.*

/**
 * 上下文中的作业：
 *
 * 协程的[Job]是上下文的一部分，并且可以使用 coroutineContext [Job] 表达式在上下文中检索它：
 */
fun main() = runBlocking<Unit> {
    isActive
    println("My job is ${coroutineContext[Job]}")
}