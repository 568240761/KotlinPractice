package coroutine


import kotlinx.coroutines.*

/**
 * 超时#2:
 *
 * [withTimeout] 抛出了 [TimeoutCancellationException] ，它是 [CancellationException] 的子类。
 * 我们之前没有在控制台上看到堆栈跟踪信息的打印。
 * 这是因为在被取消的协程中 [CancellationException] 被认为是协程执行结束的正常原因。
 * 由于取消只是一个例外，所有的资源都使用常用的方法来关闭。
 * 如果你需要做一些各类使用超时的特别的额外操作，可以使用类似 [withTimeout] 的 [withTimeoutOrNull] 函数，
 * 并把这些会超时的代码包装在 try {...} catch (e: TimeoutCancellationException) {...} 代码块中，
 * 而 [withTimeoutOrNull] 通过返回 [null] 来进行超时操作，从而替代抛出一个异常。
 */

fun main() = runBlocking {
    val result = withTimeoutOrNull(1300L) {
        repeat(1000) { i ->
            println("I'm sleeping $i ...")
            delay(500L)
        }
        "Done" // will get cancelled before it produces this result
    }
    println("Result is $result")
}