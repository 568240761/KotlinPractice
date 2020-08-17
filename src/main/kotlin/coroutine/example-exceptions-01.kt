package coroutine

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * 异常的传播
 *
 * 协程构建器有两种形式：自动传播异常（launch 与 actor）或向用户暴露异常（async 与 produce）。
 *
 * 当这些构建器用于创建一个根协程时，即该协程不是另一个协程的子协程，
 * 前者这类构建器将异常视为未捕获异常，类似 Java 的 Thread.uncaughtExceptionHandler；
 * 而后者则依赖用户来最终消费异常，例如通过 await 或 receive
 */
fun main() = runBlocking {
    val job = GlobalScope.launch { // launch 根协程
        println("Throwing exception from launch")
        throw IndexOutOfBoundsException() // 我们将在控制台打印 Thread.defaultUncaughtExceptionHandler
    }
    job.join()
    println("Joined failed job")
    val deferred = GlobalScope.async { // async 根协程
        println("Throwing exception from async")
        throw ArithmeticException() // 没有打印任何东西，依赖用户去调用等待
    }
    try {
        deferred.await()
        println("Unreached")
    } catch (e: ArithmeticException) {
        println("Caught ArithmeticException")
    }
}