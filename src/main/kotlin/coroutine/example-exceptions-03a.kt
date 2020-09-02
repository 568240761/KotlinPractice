package coroutine

import kotlinx.coroutines.*

/**
 * 取消与异常#2
 *
 * 如果一个协程遇到了 CancellationException 以外的异常，它将使用该异常取消它的父协程。
 * 这个行为无法被覆盖，并且用于为结构化的并发（structured concurrency） 提供稳定的协程层级结构。
 * CoroutineExceptionHandler 的实现并不是用于子协程。
 */

//在本例中，CoroutineExceptionHandler 总是被设置在由 GlobalScope 启动的协程中。
//将异常处理者设置在 runBlocking 主作用域内启动的协程中是没有意义的，
//尽管子协程已经设置了异常处理者， 但是主协程也总是会被取消的。
//当父协程的所有子协程都结束后，原始的异常才会被父协程处理， 见下面这个例子。
fun main() = runBlocking {
    val handler = CoroutineExceptionHandler { _, exception ->
        println("CoroutineExceptionHandler got $exception")
    }

    val job = GlobalScope.launch(handler) {
        launch { // 第一个子协程
            try {
                delay(Long.MAX_VALUE)
            } finally {
                withContext(NonCancellable) {
                    println("Children are cancelled, but exception is not handled until all children terminate")
                    delay(100)
                    println("The first child finished its non cancellable block")
                }
            }
        }
        launch { // 第二个子协程
            delay(10)
            println("Second child throws an exception")
            throw ArithmeticException()
        }
    }
    job.join()
}