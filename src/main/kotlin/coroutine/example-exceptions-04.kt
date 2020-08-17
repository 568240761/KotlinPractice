package coroutine

import kotlinx.coroutines.*
import java.io.IOException

/**
 * 异常聚合#1
 * 当协程的多个子协程因异常而失败时，一般规则是“取第一个异常”，因此将处理第一个异常。
 * 在第一个异常之后发生的所有其他异常都作为被抑制的异常绑定至第一个异常。
 *
 * 注意，这个机制当前只能在 Java 1.7 以上的版本中使用。 在 JS 和原生环境下暂时会受到限制，但将来会取消。
 */
fun main() = runBlocking {
    val handler = CoroutineExceptionHandler { _, exception ->
        println("CoroutineExceptionHandler got $exception with suppressed ${exception.suppressed.contentToString()}")
    }
    val job = GlobalScope.launch(handler) {
        launch {
            try {
                delay(Long.MAX_VALUE) // 当另一个同级的协程因 IOException  失败时，它将被取消
            } finally {
                throw ArithmeticException() // 第二个异常
            }
        }
        launch {
            delay(100)
            throw IOException() // 首个异常
        }
        delay(Long.MAX_VALUE)
    }
    job.join()
}