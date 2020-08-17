package coroutine

import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking

/**
 * 异常透明性#1：
 *
 * 声明式捕获
 *
 * 可以将 catch 操作符的声明性与处理所有异常的期望相结合，
 * 将 collect 操作符的代码块移动到 onEach 中，并将其放到 catch 操作符之前。
 * 收集该流必须由调用无参的 collect() 来触发。
 */

fun simple25(): Flow<Int> = flow {
    for (i in 1..3) {
        println("Emitting $i")
        emit(i) // 发射下一个值
    }
}

fun main() = runBlocking<Unit> {
    simple25()
        .onEach { value ->
            println(value)
            check(value <= 1) { "Collected $value" }
        }
        .catch { e -> println("Caught $e") }
        .collect()
}