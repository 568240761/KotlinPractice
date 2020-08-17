package coroutine

import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking

/**
 * 异常透明性#1：
 *
 * 发射器的代码如何封装其异常处理行为？
 *
 * 流必须对异常透明，即在 flow { ... } 构建器内部的 try/catch 块中发射值是违反异常透明性的，
 * 所以不能这样做。这样可以保证收集器抛出的一个异常能被像先前示例中那样的 try/catch 块捕获。
 *
 * 透明捕获
 *
 * 发射器可以使用 catch 操作符来保留此异常的透明性并允许封装它的异常处理。
 * catch 操作符的代码块可以分析异常并根据捕获到的异常以不同的方式对其做出反应：
 *
 * 1、可以使用 throw 重新抛出异常。
 * 2、可以使用 catch 代码块中的 emit 将异常转换为值发射出去。
 * 3、可以将异常忽略，或用日志打印，或使用一些其他代码处理它。
 *
 * catch 过渡操作符遵循异常透明性，仅捕获上游异常（catch 操作符上游的异常，但是它下面的不是）。
 * 如果 collect { ... } 块（位于 catch 之下）抛出一个异常，那么异常会逃逸。
 */

fun simple24(): Flow<Int> = flow {
    for (i in 1..3) {
        println("Emitting $i")
        emit(i) // 发射下一个值
    }
}

fun main() = runBlocking<Unit> {
    try {
        simple24().catch { e -> println(e.message) }
            .collect { value ->
                println(value)
                check(value <= 1) { "Collected $value" }
            }
    } catch (e: Throwable) {
        println("Caught $e")
    }
}