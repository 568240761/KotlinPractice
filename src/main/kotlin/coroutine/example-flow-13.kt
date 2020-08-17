package coroutine

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

/**
 * 流上下文：
 *
 * 流的收集总是在调用协程的上下文中发生。
 * 例如，如果有一个流 foo，然后以下代码在它的编写者指定的上下文中运行，
 * 而无论流 foo的实现细节如何：
 *
 * withContext(context) {
 *      foo.collect { value ->
 *          println(value) // 运行在指定上下文中
 *      }
 * }
 *
 * 流的该属性称为 上下文保存 。
 * 所以默认的， flow { ... } 构建器中的代码运行在相应流的收集器提供的上下文中。
 * 举例来说，考虑打印线程的 foo 的实现，它被调用并发射三个数字：
 */
fun log13(msg: String) = println("[${Thread.currentThread().name}] $msg")

fun foo13(): Flow<Int> = flow {
    log13("Started foo flow")
    for (i in 1..3) {
        emit(i)
    }
}

fun main() = runBlocking<Unit> {
    //由于 foo13().collect 是在主线程调用的，则 foo 的流主体也是在主线程调用的。
    //这是快速运行或异步代码的理想默认形式，它不关心执行的上下文并且不会阻塞调用者。
    foo13().collect { value -> log13("Collected $value") }
}