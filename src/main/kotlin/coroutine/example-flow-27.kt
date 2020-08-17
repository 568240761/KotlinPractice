package coroutine

import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import java.lang.RuntimeException

/**
 * 流完成
 *
 * 当流收集完成时（普通情况或异常情况），它可能需要执行一个动作。
 * 你可能已经注意到，它可以通过两种方式完成：命令式或声明式。
 *
 * 声明式处理:
 *
 * 对于声明式，流拥有 onCompletion 过渡操作符，它在流完全收集时调用。
 */

fun simple27(): Flow<Int> = flow {
    for (i in 1..3) {
        println("Emitting $i")
        emit(i) // 发射下一个值
    }
}

fun simple27a(): Flow<Int> = flow {
    for (i in 1..3) {
        println("Emitting $i")
        emit(i) // 发射下一个值
        throw RuntimeException()
    }
}

fun main() = runBlocking<Unit> {
    simple27()
        .onCompletion { println("Done") }
        .collect { value -> println(value) }

    //onCompletion 的主要优点是其 lambda 表达式的可空参数 Throwable ,
    //可以用于确定流收集是正常完成还是有异常发生。
    simple27a()
        .onCompletion { cause -> if (cause != null) println("Flow completed exceptionally") }
        .catch { cause -> println("Caught exception") }
        .collect { value -> println(value) }

    //与 catch 操作符的另一个不同点是 onCompletion 能观察到所有异常，
    //并且仅在上游流成功完成（没有取消或失败）的情况下接收一个 null 异常。
    simple27()
        .onCompletion { cause -> println("Flow completed with $cause") }
        .collect { value ->
            check(value <= 1) { "Collected $value" }
            println(value)
        }
}