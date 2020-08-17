package coroutine

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import java.lang.System.currentTimeMillis

/**
 * 组合多个流-Combine：
 *
 * 当流表示一个变量或操作的最新值时，可能需要执行计算，这依赖于相应流的最新值，
 * 并且每当上游流产生值的时候都需要重新计算。这种相应的操作符家族称为 combine。
 */
fun main() = runBlocking<Unit> {
    val nums = (1..3).asFlow()
        .onEach { delay(300) } // 发射数字 1..3，间隔 300 毫秒
    val strs = flowOf("one", "two", "three")
        .onEach { delay(400) } // 每 400 毫秒发射一次字符串

    val startTime = currentTimeMillis() // 记录开始的时间

    nums.zip(strs) { a, b -> "$a -> $b" } // 使用“zip”组合单个字符串
        .collect { value -> // 收集并打印
            println("$value at ${currentTimeMillis() - startTime} ms from start")
        }

    // nums 或 strs 流中的每次发射都会打印一行
    nums.combine(strs) { a, b -> "$a -> $b" } // 使用“combine”组合单个字符串
        .collect { value -> // 收集并打印
            println("$value at ${currentTimeMillis() - startTime} ms from start")
        }
}