package coroutine

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

/**
 * 流：
 *
 * 使用 List 结果类型，意味着我们只能一次返回所有值。
 * 为了表示异步计算的值流，我们可以使用 Flow 类型；
 * 正如同步计算值会使用 Sequence 类型。
 */
fun foo4a(): Flow<Int> = flow { // 流构建器
    for (i in 1..3) {
//        delay(100) // 假装我们在这里做了一些有用的事情
        Thread.sleep(100)
        emit(i) // 发送下一个值
    }
}

fun main() = runBlocking<Unit> {
    // 启动并发的协程以验证主线程并未阻塞
    launch {
        for (k in 1..3) {
            println("I'm not blocked $k")
            delay(100)
        }
    }
    // 收集这个流
    foo4a().collect { value -> println(value) }
}