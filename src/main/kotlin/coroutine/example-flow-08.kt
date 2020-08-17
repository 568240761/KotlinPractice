package coroutine

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

/**
 * 过渡流操作符：
 *
 * 可以使用操作符转换流，就像使用集合与序列一样。 过渡操作符应用于上游流，并返回下游流。
 * 这些操作符也是冷操作符，就像流一样。这类操作符本身不是挂起函数。它运行的速度很快，
 * 返回新的转换流的定义。基础的操作符拥有相似的名字，比如 map 与 filter。
 * 流与序列的主要区别在于这些操作符中的代码可以调用挂起函数。
 * 举例来说，一个请求中的流可以使用 map 操作符映射出结果，
 * 即使执行一个长时间的请求操作也可以使用挂起函数来实现。
 */
suspend fun performRequest(request: Int): String {
    delay(1000) // 模仿长时间运行的异步工作
    return "response $request"
}

fun main() = runBlocking<Unit> {
    (1..3).asFlow() // 一个请求流
        .map { request -> performRequest(request) }
        .collect { response -> println(response) }
}