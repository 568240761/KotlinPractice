package coroutine

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

/**
 * 末端流操作符：
 *
 * collect 是最基础的末端操作符，但是还有另外一些更方便使用的末端操作符：
 * 转化为各种集合，例如 toList 与 toSet。
 * 获取第一个（first）值与确保流发射单个（single）值的操作符。
 * 使用 reduce 与 fold 将流规约到单个值。
 */
fun main() = runBlocking<Unit> {
    val sum = (1..5).asFlow()
        .map { it * it } // 数字 1 至 5 的平方
        .reduce { a, b -> a + b } // 求和（末端操作符）
    println(sum)

    val sum1 = (1..5).asFlow()
        .map { it * it } // 数字 1 至 5 的平方
        .fold(10){ a, b -> a + b } // 求和+固定值（末端操作符）
    println(sum1)
}