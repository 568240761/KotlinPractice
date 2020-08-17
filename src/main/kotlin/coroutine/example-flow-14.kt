package coroutine

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

/**
 * withContext 发出错误：
 *
 * 长时间运行的消耗 CPU 的代码也许需要在 Dispatchers.Default 上下文中执行，
 * 并且更新 UI 的代码也许需要在 Dispatchers.Main 中执行。
 * 通常，withContext 用于在 Kotlin 协程中改变代码的上下文，
 * 但是 flow {...} 构建器中的代码必须遵循上下文保存属性，并且不允许从其他上下文中发射（emit）。
 */
fun foo14(): Flow<Int> = flow {
    // 在流构建器中更改消耗 CPU 代码的上下文的错误方式
    withContext(Dispatchers.Default) {
        for (i in 1..3) {
            Thread.sleep(100) // 假装我们以消耗 CPU 的方式进行计算
            emit(i) // 发射下一个值
        }
    }
}

fun main() = runBlocking<Unit> {
    foo14().collect { value -> println(value) }
}