package coroutine

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

/**
 * 组合多个流-zip：
 *
 * 就像 Kotlin 标准库中的 Sequence.zip 扩展函数一样，
 * 流拥有一个 zip 操作符用于组合两个流中的相关值。
 */
fun main() = runBlocking<Unit> {
    val nums = (1..3).asFlow() //  数字 1..3
    val strs = flowOf("one", "two", "three") // 字符串
    nums.zip(strs) { a, b -> "$a -> $b" } //  组合单个字符串
        .collect { println(it) } //  收集并打印
}