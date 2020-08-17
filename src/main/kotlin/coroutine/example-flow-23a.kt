package coroutine

import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking

/**
 * 流异常：
 *
 * 收集器 try 与 catch#2
 *
 * 当运算符中的发射器或代码抛出异常时，流收集可以带有异常的完成。
 */

fun simple23a(): Flow<String> =
    flow {
        for (i in 1..3) {
            println("Emitting $i")
            emit(i) // 发射下一个值
        }
    }.map { value ->
        check(value <= 1) { "Crashed on $value" }
        "string $value"
    }

fun main() = runBlocking<Unit> {
    try {
        simple23a().collect { value ->
            println(value)
        }
    } catch (e: Throwable) {
        println("Caught $e")
    }
}