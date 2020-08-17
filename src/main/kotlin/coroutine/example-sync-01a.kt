package coroutine

import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

/**
 * volatile 无济于事
 *
 * 有一种常见的误解：volatile 可以解决并发问题。
 */
suspend fun massiveRuna(action: suspend () -> Unit) {
    val n = 100  // number of coroutines to launch
    val k = 1000 // times an action is repeated by each coroutine
    val time = measureTimeMillis {
        coroutineScope {
            // scope for coroutines
            repeat(n) {
                launch {
                    repeat(k) { action() }
                }
            }
        }
    }
    println("Completed ${n * k} actions in $time ms")
}

@Volatile // 在 Kotlin 中 `volatile` 是一个注解
var counter1 = 0

fun main() = runBlocking {
    withContext(Dispatchers.Default) {
        massiveRuna {
            counter1++
        }
    }

    // 但我们最后仍然没有得到“Counter = 100000”这个结果，
    // 因为 volatile 变量保证可线性化（这是“原子”的技术术语）读取和写入变量，
    // 但在大量动作（在我们的示例中即“递增”操作）发生时并不提供原子性。
    println("Counter = $counter1")
}