package coroutine

import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlin.system.measureTimeMillis

/**
 * 互斥
 *
 * 该问题的互斥解决方案：使用永远不会同时执行的关键代码块来保护共享状态的所有修改。
 * 在阻塞的世界中，你通常会为此目的使用 synchronized 或者 ReentrantLock。
 * 在协程中的替代品叫做 Mutex 。它具有 lock 和 unlock 方法， 可以隔离关键的部分。
 * 关键的区别在于 Mutex.lock() 是一个挂起函数，它不会阻塞线程。
 * 还有 withLock 扩展函数，可以方便的替代常用的 mutex.lock(); try { …… } finally { mutex.unlock() }。
 */
suspend fun massiveRun5(action: suspend () -> Unit) {
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

val mutex = Mutex()
var counter5 = 0

fun main() = runBlocking {
    withContext(Dispatchers.Default) {
        massiveRun5 {
            // 用锁保护每次自增
            mutex.withLock {
                counter5++
            }
        }
    }

    //此示例中锁是细粒度的，因此会付出一些代价。
    // 但是对于某些必须定期修改共享状态的场景，它是一个不错的选择，但是没有自然线程可以限制此状态。
    println("Counter = $counter5")
}