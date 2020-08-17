package coroutine

import kotlinx.coroutines.*
import kotlin.system.*

/**
 * 默认顺序调用:
 *
 * 假设我们在不同的地方定义了两个进行某种调用远程服务或者进行计算的挂起函数。
 * 我们使用普通的顺序来进行调用，因为这些代码是运行在协程中的，只要像常规的代码一样顺序都是默认的。
 */
fun main() = runBlocking<Unit> {
    val time = measureTimeMillis {
        val one = doSomethingUsefulOne()
        val two = doSomethingUsefulTwo()
        println("The answer is ${one + two}")
    }
    println("Completed in $time ms")
}

suspend fun doSomethingUsefulOne(): Int {
    delay(1000L) // pretend we are doing something useful here
    return 13
}

suspend fun doSomethingUsefulTwo(): Int {
    delay(1000L) // pretend we are doing something useful here, too
    return 29
}