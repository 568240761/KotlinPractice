package coroutine

import kotlinx.coroutines.*

/**
 * 取消是协作的:
 *
 * 协程的取消是协作的。一段协程代码必须协作才能被取消。
 * 所有 [kotlinx.coroutines] 中的挂起函数都是可被取消的 。
 * 它们检查协程的取消，并在取消时抛出[CancellationException]。
 * 然而，如果协程正在执行计算任务，并且没有检查取消的话，
 * 那么它是不能被取消的，就如如下示例代码所示：
 */
fun main() = runBlocking {
    val startTime = System.currentTimeMillis()
    val job = launch(Dispatchers.Default) {
        var nextPrintTime = startTime
        var i = 0
        while (i < 5) { // computation loop, just wastes CPU
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