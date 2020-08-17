package coroutine

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

/**
 * 流构建器：
 *
 * 先前示例中的 flow { ... } 构建器是最基础的一个。还有其它构建器使流的声明更简单：
 * flowOf 构建器定义了一个发射固定值集的流。
 * 使用 .asFlow() 扩展函数，可以将各种集合与序列转换为流。
 * 因此，从流中打印从 1 到 3 的数字的示例可以写成：
 */
fun main() = runBlocking<Unit> {
    //sampleStart
    // 将一个整数区间转化为流
    (1..3).asFlow().collect { value -> println(value) }

    println("flowOf")

    flowOf(4,5,6).collect { value -> println(value) }
    //sampleEnd
}