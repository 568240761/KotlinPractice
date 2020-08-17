package coroutine

import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis

/**
 * 使用async并发：
 *
 * 如果 [doSomethingUsefulOne] 与 [doSomethingUsefulTwo] 之间没有依赖，并且我们想更快的得到结果，
 * 想让它们并发进行 ，[async]可以帮助我们。
 * 在概念上，[async] 就类似于 [launch]。
 * 它启动了一个单独的协程，并与其它所有的协程一起并发的工作。
 * 不同之处在于 [launch] 返回一个 [Job] 并且不附带任何结果值，
 * 而 [async] 返回一个 [Deferred] —— 一个轻量级的非阻塞 [future]，这代表了一个将会在稍后提供结果的 [promise]。
 * 你可以使用 await() 在一个延期的值上得到它的最终结果， 但是 [Deferred] 也是一个 [Job] ，所以如果需要的话，你可以取消它。
 */
fun main() = runBlocking<Unit> {
    val time = measureTimeMillis {
        val one = async {
            doSomethingUsefulThree()
        }
        val two = async {
            doSomethingUsefulFour()
        }
        println("The answer is ${one.await() + two.await()}")
    }
    println("Completed in $time ms")
}

suspend fun doSomethingUsefulThree(): Int {
    delay(1000L) // pretend we are doing something useful here
    return 13
}

suspend fun doSomethingUsefulFour(): Int {
    delay(1000L) // pretend we are doing something useful here, too
    return 29
}