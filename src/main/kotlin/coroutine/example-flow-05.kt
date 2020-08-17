package coroutine

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

/**
 * 流是冷的：
 *
 * Flow 是一种类似于序列的冷流；flow 构建器中的代码直到流被收集的时候才运行。
 */
fun foo5(): Flow<Int> = flow {
    println("Flow started")
    for (i in 1..3) {
        delay(100)
        emit(i)
    }
}

fun main() = runBlocking<Unit> {
    println("Calling foo...")
    val flow = foo5()
    println("Calling collect...")
    flow.collect { value -> println(value) }
    println("Calling collect again...")
    flow.collect { value -> println(value) }
}