package coroutine

import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking

/**
 * 流完成
 *
 * 当流收集完成时（普通情况或异常情况），它可能需要执行一个动作。
 * 你可能已经注意到，它可以通过两种方式完成：命令式或声明式。
 *
 * 命令式 finally 块:
 *
 * 除了 try/catch 之外，收集器还能使用 finally 块在 collect 完成时执行一个动作
 */

fun simple26(): Flow<Int> = flow {
    for (i in 1..3) {
        println("Emitting $i")
        emit(i) // 发射下一个值
    }
}

fun main() = runBlocking<Unit> {
    try {
        simple26().collect { value -> println(value) }
    } finally {
        println("Done")
    }
}