package coroutine

import kotlinx.coroutines.*
import java.io.IOException

/**
 * 异常聚合#2
 *
 * 取消异常是透明的，默认情况下是未包装。
 */
fun main() = runBlocking {
    val handler = CoroutineExceptionHandler { _, exception ->
        println("CoroutineExceptionHandler got $exception")
    }
    val job = GlobalScope.launch(handler) {
        val inner = launch { // 该栈内的协程都将被取消
            launch {
                launch {
                    throw IOException() // 原始异常
                }
            }
        }
        try {
            inner.join()
        } catch (e: CancellationException) {
            println("Rethrowing CancellationException with original cause")
            throw e // 取消异常被重新抛出，但原始 IOException 得到了处理
        }
    }
    job.join()
}