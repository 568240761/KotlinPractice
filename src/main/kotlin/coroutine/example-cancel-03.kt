package coroutine

import kotlinx.coroutines.*

/**
 * 使计算代码可取消:
 *
 * 我们有两种方法来使执行计算的代码可以被取消。
 *
 * 第一种方法是定期调用挂起函数来检查取消；对于这种目的 [yield] 是一个好的选择；
 * 另一种方法是显式的检查取消状态。
 */
fun main() = runBlocking {
    val startTime = System.currentTimeMillis()
    val job = launch(Dispatchers.Default) {
        var nextPrintTime = startTime
        var i = 0
        while (isActive) { // cancellable computation loop
            // print a message twice a second
            if (System.currentTimeMillis() >= nextPrintTime) {
                println("job: I'm sleeping ${i++} ...")
                nextPrintTime += 500L
            }
        }
    }
    delay(1300L) // delay a bit
    println("main: I'm tired of waiting!")
    job.cancelAndJoin() // cancels the job and waits for its completion
    println("main: Now I can quit.")
}