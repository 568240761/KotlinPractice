package coroutine

import kotlinx.coroutines.*

/**
 * 挂起函数：
 *
 * 如果计算过程阻塞运行该代码的主线程，我们可以使用 suspend 修饰符标记函数 foo3 ，
 * 这样它就可以在不阻塞的情况下执行其工作并将结果作为列表返回。
 */

suspend fun foo3(): List<Int> {
    delay(1000) // 假装我们在这里做了一些异步的事情
    return listOf(1, 2, 3)
}

fun main() = runBlocking<Unit> {
    foo3().forEach { value -> println(value) }
}