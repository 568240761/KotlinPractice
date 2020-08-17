package coroutine

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

/**
 * flowOn 操作符：
 *
 * 该函数用于更改流发射的上下文。 以下示例展示了更改流上下文的正确方法，
 * 该示例还通过打印相应线程的名字以展示它们的工作方式：
 *
 * 注意：
 * flowOn 操作符已改变流的默认顺序性。
 * 现在收集发生在一个协程中（“coroutine#1”），
 * 而发射发生在运行于另一个线程中与收集协程并发运行的另一个协程（“coroutine#2”）中。
 * 当上游流必须改变其上下文中的 CoroutineDispatcher 的时候，flowOn操作符创建了另一个协程。
 */
fun log15(msg: String) = println("[${Thread.currentThread().name}] $msg")

fun foo15(): Flow<Int> = flow {
    for (i in 1..3) {
        Thread.sleep(100) //  假装我们以消耗 CPU 的方式进行计算
        log15("Emitting $i")
        emit(i) //  发射下一个值
    }
}.flowOn(Dispatchers.Default) //  在流构建器中改变消耗 CPU 代码上下文的正确方式

fun main() = runBlocking<Unit> {
    foo15().collect { value ->
        log15("Collected $value")
    }
}