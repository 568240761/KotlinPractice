package coroutine

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

/**
 * 流取消：
 *
 * 流采用与协程同样的协作取消。
 * 然而，流的基础设施未引入其他取消点，取消完全透明。
 * 流的收集可以在当流在一个可取消的挂起函数（例如 delay）中挂起的时候取消，否则不能取消。
 */
fun foo(): Flow<Int> = flow {
    for (i in 1..3) {
        delay(100)
        println("Emitting $i")
        emit(i)
    }
}

fun main() = runBlocking<Unit> {
    withTimeoutOrNull(250) { // 在 250 毫秒后超时
        foo().collect { value -> println(value) }
    }
    println("Done")
}