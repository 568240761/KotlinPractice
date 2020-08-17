package coroutine

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlin.system.*

/**
 * 缓冲#1：
 *
 * 从收集流所花费的时间来看，将流的不同部分运行在不同的协程中将会很有帮助，
 * 特别是当涉及到长时间运行的异步操作时。
 * 例如，考虑一种情况， foo() 流的发射很慢，它每花费100 毫秒才产生一个元素；
 * 而收集器也非常慢， 需要花费 300 毫秒来处理元素。
 * 让我们看看从该流收集三个数字要花费多长时间：
 */
fun foo16(): Flow<Int> = flow {
    for (i in 1..3) {
        delay(100) // 假装我们异步等待了 100 毫秒
        emit(i) // 发射下一个值
    }
}

fun main() = runBlocking<Unit> {
    val time = measureTimeMillis {
        foo16().collect { value ->
            delay(300) // 假装我们花费 300 毫秒来处理它
            println(value)
        }
    }
    println("Collected in $time ms")
}