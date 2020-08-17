package coroutine

import kotlinx.coroutines.*

/**
 * 运行不能取消的代码块:
 *
 * 在前一个例子中任何尝试在 [finally] 块中调用挂起函数的行为都会抛出[CancellationException]，
 * 因为这里持续运行的代码是可以被取消的。
 * 通常，这并不是一个问题，所有良好的关闭操作（关闭一个文件、取消一个作业、或是关闭任何一种通信通道）;
 * 通常都是非阻塞的，并且不会调用任何挂起函数。
 * 然而，在真实的案例中，当你需要挂起一个被取消的协程，
 * 你可以将相应的代码包装在 withContext(NonCancellable) {……} 中，
 * 并使用 [withContext] 函数以及 [NonCancellable] 上下文，见如下示例所示：
 */
fun main() = runBlocking {
    val job = launch {
        try {
            repeat(1000) { i ->
                println("job: I'm sleeping $i ...")
                delay(500L)
            }
        } finally {
            withContext(NonCancellable) {
                println("job: I'm running finally")
                delay(4000L)
                println("job: And I've just delayed for 1 sec because I'm non-cancellable")
            }
        }
    }
    delay(1300L) // delay a bit
    println("main: I'm tired of waiting!")
    job.cancelAndJoin() // cancels the job and waits for its completion
    println("main: Now I can quit.")
}