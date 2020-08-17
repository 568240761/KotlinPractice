package coroutine

import kotlinx.coroutines.*

/**
 * 取消协程的执行：
 *
 * 在一个长时间运行的应用程序中，你也许需要对你的后台协程进行细粒度的控制。
 * 比如说，一个用户也许关闭了一个启动了协程的界面，
 * 那么现在协程的执行结果已经不再被需要了，这时，它应该是可以被取消的。
 * 该 [launch] 函数返回了一个可以被用来取消运行中的协程的[Job]。
 * 也可以使 [Job] 挂起的函数 [cancelAndJoin] 它合并了对 [cancel] 以及 [join] 的调用。
 */
fun main() = runBlocking {
    val job = launch {
        repeat(1000) { i ->
            println("job: I'm sleeping $i ...")
            delay(500L)
        }
    }
    delay(1300L) // delay a bit
    println("main: I'm tired of waiting!")
    job.cancel() // cancels the job
    job.join() // waits for job's completion
    println("main: Now I can quit.")
}