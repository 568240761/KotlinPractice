package coroutine

import kotlinx.coroutines.*

/**
 * 组合上下文中的元素:
 *
 * 有时我们需要在协程上下文中定义多个元素，我们可以使用 + 操作符来实现。
 */
fun main() = runBlocking<Unit> {
    //以显式指定一个调度器来启动协程并且同时显式指定一个命名
    launch(Dispatchers.Default + CoroutineName("test")) {
        println("I'm working in thread ${Thread.currentThread().name}")
    }
}