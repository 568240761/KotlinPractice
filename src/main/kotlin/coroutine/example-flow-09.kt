package coroutine

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

/**
 * 转换操作符：
 *
 * 在流转换操作符中，最通用的一种称为 transform。
 * 它可以用来模仿简单的转换，例如 map与 filter，以及实施更复杂的转换。
 * 使用 transform 操作符，我们可以发射任意值任意次。
 * 比如说，使用 transform 我们可以在执行长时间运行的异步请求之前发射一个字符串并跟踪这个响应。
 */
suspend fun performRequest9(request: Int): String {
    delay(1000) // 模仿长时间运行的异步任务
    return "response $request"
}

fun main() = runBlocking<Unit> {
    (1..3).asFlow() //  一个请求流
        .transform { request ->
            emit("Making request $request")
            emit(performRequest9(request))
        }
        .collect { response -> println(response) }
}