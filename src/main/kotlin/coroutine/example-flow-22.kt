package coroutine

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import java.lang.System.currentTimeMillis

/**
 * 展平流：
 *
 * flatMapConcat:
 * 串联模式由flatMapConcat和flattenConcat运算符实现。
 * 它们是相应序列运算符的最直接类似物，他们等待内部流程完成，
 * 然后开始收集下一个示例，如以下示例所示。
 *
 * flatMapMerge：
 * 另一种展平模式是同时收集所有传入流并将其值合并为单个流，以便尽快发出值。
 * 它由flatMapMerge和flattenMerge运算符实现。
 * 它们都接受一个可选的并发参数，该参数限制了同时收集的并发流的数量（默认情况下它等于DEFAULT_CONCURRENCY）。
 *
 * flatMapLatest：
 * 与collectLatest运算符类似的方式，存在对应的“最新”展平模式，
 * 在该模式下，一旦发出新流，就会取消先前流的集合。
 * 它由flatMapLatest运算符实现。
 *
 * 请注意，flatMapLatest取消了其块中的所有代码（在本示例中为{requestFlow（it）}）为新值。
 * 在此特定示例中，这没有什么区别，因为对requestFlow本身的调用是快速，非挂起的并且无法取消。
 * 但是，如果要在其中使用诸如delay之类的暂停功能，它将显示出来。
 */
fun requestFlow(i: Int): Flow<String> = flow {
    emit("$i: First")
    delay(500) // wait 500 ms
    emit("$i: Second")
}

fun main() = runBlocking<Unit> {
    val startTime = currentTimeMillis() // remember the start time
    (1..3).asFlow().onEach { delay(100) } // a number every 100 ms
//        .flatMapConcat { requestFlow(it) }
        .flatMapMerge { requestFlow(it) }
//        .flatMapLatest { requestFlow(it) }
        .collect { value -> // collect and print
            println("$value at ${currentTimeMillis() - startTime} ms from start")
        }
}